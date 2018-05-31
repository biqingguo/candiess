package com.itranswarp.crypto.candiess.web.modules.admin.canal;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.otter.canal.parse.inbound.SinkFunction;
import com.itranswarp.crypto.canal.vo.ChangeEventType;
import com.itranswarp.crypto.canal.vo.SendLogVo;
import com.itranswarp.crypto.common.SpringUtil;
import com.itranswarp.crypto.manage.model.CanalPositionsConfig;
import com.itranswarp.crypto.manage.service.SendMessageToMq;
import com.itranswarp.crypto.manage.util.JsonUtil;
import com.itranswarp.warpdb.WarpDb;
import com.taobao.tddl.dbsync.binlog.LogEvent;
import com.taobao.tddl.dbsync.binlog.event.DeleteRowsLogEvent;
import com.taobao.tddl.dbsync.binlog.event.RotateLogEvent;
import com.taobao.tddl.dbsync.binlog.event.RowsLogBuffer;
import com.taobao.tddl.dbsync.binlog.event.RowsLogEvent;
import com.taobao.tddl.dbsync.binlog.event.TableMapLogEvent;
import com.taobao.tddl.dbsync.binlog.event.TableMapLogEvent.ColumnInfo;
import com.taobao.tddl.dbsync.binlog.event.UpdateRowsLogEvent;
import com.taobao.tddl.dbsync.binlog.event.WriteRowsLogEvent;

public class SinkToMq implements SinkFunction<LogEvent> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private Charset charset = Charset.forName("UTF-8");
	protected String binlogFileName;
	protected long position;
	private Thread thread;
	private long id;

	public SinkToMq(Charset charset) {
		this.charset = charset;
		/**
		 * 设置守护线程，定时刷新进度到数据库
		 */
		this.thread = new Thread(new Runnable() {
			long positionOld = position;

			public void run() {
				while (true) {
					try {
						/**
						 * binlogFileName不能为空 positionbinlog的下标不能为0 而且进度未更新到数据库
						 */
						if (StringUtils.isNotEmpty(binlogFileName) && position > 0 && positionOld != position) {
							CanalPositionsConfig canalPositionsConfig = new CanalPositionsConfig();
							canalPositionsConfig.journalName = binlogFileName;
							canalPositionsConfig.dbConfigId = id;
							canalPositionsConfig.position = position;
							canalPositionsConfig.timestamp = System.currentTimeMillis();
							positionOld = position;
							WarpDb warpDb = (WarpDb) SpringUtil.getBean("manageWarpDB");
							List<CanalPositionsConfig> canalPositionsConfigList = warpDb
									.list("SELECT * FROM canalpositionsconfig WHERE dbConfigId = ?", id);
							if (canalPositionsConfigList == null || canalPositionsConfigList.isEmpty()) {
								warpDb.save(canalPositionsConfig);
							} else {
								canalPositionsConfig.id = canalPositionsConfigList.get(0).id;
								warpDb.update(canalPositionsConfig);
							}
							logger.info("canal守护进程保存进度:{}", JsonUtil.toJson(canalPositionsConfig));
						}
						Thread.sleep(1000l);
					} catch (InterruptedException e) {
						logger.error("canal守护线程异常:{}", ExceptionUtils.getStackTrace(e));
					}
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	@Override
	public boolean sink(LogEvent event) {

		int eventType = event.getHeader().getType();
		switch (eventType) {
		/**
		 * 更新binlog文件位置
		 */
		case LogEvent.ROTATE_EVENT:
			this.position = event.getHeader().getLogPos();
			this.binlogFileName = ((RotateLogEvent) event).getFilename();
			break;
		/**
		 * 插入数据
		 */
		case LogEvent.WRITE_ROWS_EVENT_V1:
		case LogEvent.WRITE_ROWS_EVENT:
			this.position = event.getHeader().getLogPos();
			writeRowsLogEvent((WriteRowsLogEvent) event);
			break;
		/**
		 * 更新数据
		 */
		case LogEvent.UPDATE_ROWS_EVENT_V1:
		case LogEvent.UPDATE_ROWS_EVENT:
			this.position = event.getHeader().getLogPos();
			updateRowsLogEvent((UpdateRowsLogEvent) event);
			break;
		/**
		 * 删除数据
		 */
		case LogEvent.DELETE_ROWS_EVENT_V1:
		case LogEvent.DELETE_ROWS_EVENT:
			this.position = event.getHeader().getLogPos();
			deleteRowsLogEvent((DeleteRowsLogEvent) event);
			break;
		/**
		 * 查询 暂时放弃保存，因为后台不需要事务类的消息
		 */
		case LogEvent.QUERY_EVENT:
			break;
		/**
		 * 查询 暂时放弃保存，因为后台不需要事务类的消息
		 */
		case LogEvent.ROWS_QUERY_LOG_EVENT:
			break;
		/**
		 * 查询 暂时放弃保存，因为后台不需要事务类的消息
		 */
		case LogEvent.ANNOTATE_ROWS_EVENT:
			break;
		/**
		 * 事务Id 暂时放弃保存，因为后台不需要事务类的消息
		 */
		case LogEvent.XID_EVENT:
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * 插入事件
	 * 
	 * @param event
	 */
	public void writeRowsLogEvent(WriteRowsLogEvent event) {
		try {
			SendMessageToMq sendMessageToMq = (SendMessageToMq) SpringUtil.getBean("sendMessageToMq");
			RowsLogBuffer buffer = event.getRowsBuf(charset.name());
			BitSet columns = event.getColumns();
			while (buffer.nextOneRow(columns)) {
				// 处理row记录
				// insert的记录放在before字段中
				List<Object> parseOneRow = parseOneRow(event, buffer, columns, true);
				SendLogVo sendLogVo = new SendLogVo();
				sendLogVo.setLogfileName(binlogFileName);
				sendLogVo.setLogfileOffse(event.getHeader().getLogPos());
				sendLogVo.setDbName(event.getTable().getDbName());
				sendLogVo.setTableName(event.getTable().getTableName());
				sendLogVo.setChangeEventType(ChangeEventType.INSERT);
				sendLogVo.setData(parseOneRow);
				sendMessageToMq.sendMq(sendLogVo, id);
				// System.out.println(JsonUtil.toJson(sendLogVo));
			}
		} catch (Exception e) {
			throw new RuntimeException("parse row data failed.", e);
		}
	}

	/**
	 * 修改事件
	 * 
	 * @param event
	 */
	public void updateRowsLogEvent(UpdateRowsLogEvent event) {
		try {
			SendMessageToMq sendMessageToMq = (SendMessageToMq) SpringUtil.getBean("sendMessageToMq");
			RowsLogBuffer buffer = event.getRowsBuf(charset.name());
			BitSet columns = event.getColumns();
			BitSet changeColumns = event.getChangeColumns();
			while (buffer.nextOneRow(columns)) {
				// 处理row记录
				List<Object> parseOneRowOld = parseOneRow(event, buffer, columns, false);
				if (!buffer.nextOneRow(changeColumns)) {
					break;
				}
				List<Object> parseOneRowNew = parseOneRow(event, buffer, changeColumns, true);
				SendLogVo sendLogVo = new SendLogVo();
				sendLogVo.setLogfileName(binlogFileName);
				sendLogVo.setLogfileOffse(event.getHeader().getLogPos());
				sendLogVo.setDbName(event.getTable().getDbName());
				sendLogVo.setTableName(event.getTable().getTableName());
				sendLogVo.setChangeEventType(ChangeEventType.UPDATE);
				sendLogVo.setData(parseOneRowNew);
				sendLogVo.setOlddata(parseOneRowOld);
				sendMessageToMq.sendMq(sendLogVo, id);
				// System.out.println(JsonUtil.toJson(sendLogVo));
			}
		} catch (Exception e) {
			throw new RuntimeException("parse row data failed.", e);
		}
	}

	/**
	 * 删除事件
	 * 
	 * @param event
	 */
	public void deleteRowsLogEvent(DeleteRowsLogEvent event) {
		try {
			SendMessageToMq sendMessageToMq = (SendMessageToMq) SpringUtil.getBean("sendMessageToMq");
			RowsLogBuffer buffer = event.getRowsBuf(charset.name());
			BitSet columns = event.getColumns();
			while (buffer.nextOneRow(columns)) {
				// delete的记录放在before字段中
				List<Object> parseOneRow = parseOneRow(event, buffer, columns, false);
				SendLogVo sendLogVo = new SendLogVo();
				sendLogVo.setLogfileName(binlogFileName);
				sendLogVo.setLogfileOffse(event.getHeader().getLogPos());
				sendLogVo.setDbName(event.getTable().getDbName());
				sendLogVo.setTableName(event.getTable().getTableName());
				sendLogVo.setChangeEventType(ChangeEventType.DELETE);
				sendLogVo.setData(parseOneRow);
				sendMessageToMq.sendMq(sendLogVo, id);
				// System.out.println(JsonUtil.toJson(sendLogVo));
			}
		} catch (Exception e) {
			throw new RuntimeException("parse row data failed.", e);
		}
	}

	protected List<Object> parseOneRow(RowsLogEvent event, RowsLogBuffer buffer, BitSet cols, boolean isAfter)
			throws UnsupportedEncodingException {
		List<Object> list = new ArrayList<>();
		TableMapLogEvent map = event.getTable();
		if (map == null) {
			throw new RuntimeException("not found TableMap with tid=" + event.getTableId());
		}

		final int columnCnt = map.getColumnCnt();
		final ColumnInfo[] columnInfo = map.getColumnInfo();

		for (int i = 0; i < columnCnt; i++) {
			if (!cols.get(i)) {
				continue;
			}

			ColumnInfo info = columnInfo[i];
			buffer.nextValue(info.type, info.meta);

			if (buffer.isNull()) {
				// 对空特殊处理，赋值为""
				list.add("");
			} else {
				final Serializable value = buffer.getValue();
				if (value instanceof byte[]) {
					list.add(new String((byte[]) value));
				} else {
					list.add(value);
				}
			}
		}
		return list;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
