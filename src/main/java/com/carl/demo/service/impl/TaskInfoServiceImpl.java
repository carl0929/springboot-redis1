package com.carl.demo.service.impl;

import com.carl.demo.entity.TaskInfo;
import com.carl.demo.mapper.ITaskInfoMapper;
import com.carl.demo.service.ITaskInfoService;
import com.carl.demo.utils.Objects;
import com.carl.demo.utils.RediskeyConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service
public class TaskInfoServiceImpl implements ITaskInfoService {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(TaskInfoServiceImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ITaskInfoMapper mTaskInfoMapper;



//	@Autowired
//	@Qualifier("countriesInfoService")
//	private ICountriesInfoService mCountriesInfoService;

    @Override
    public boolean addRecord(TaskInfo info) {
        if(null == info) {
            return false;
        }

        mTaskInfoMapper.addTask(getTableName(), info);

        return info.getInd() > 0;
    }

    @Override
    public void deleteRecordByIndex(int ind) {
        mTaskInfoMapper.deleteTaskByIndex(getTableName(), ind);
        String key = RediskeyConstants.TASKS_INFO_KEY + ind ;
        redisTemplate.delete(key);
    }

    /**
     * 记录每日完成数
     *
     * @param taskId
     * @param count
     * @return 操作结果
     */
    @Override
    public boolean recordTaskDoneCount(String taskId, int count) {
        if(StringUtils.isEmpty(taskId)) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "taskId为空，无法更新任务完成数量！");
            return false;
        }

        mTaskInfoMapper.updateTaskDoneCount(getTableName(), taskId, count);

        return true;
    }
    @Override
    public TaskInfo findRecordByIndex(int ind) {
        if(ind <= 0) {
            Logger.getAnonymousLogger().log(Level.SEVERE, MessageFormat.format("序列号【{0}】不合法，无法查询", ind));
            return null;
        }
        //return mTaskInfoMapper.findRecordByIndex(getTableName(), ind);
        TaskInfo record = null;
        try {
            String key = RediskeyConstants.TASKS_INFO_KEY + ind ;
            record = (TaskInfo) redisTemplate.opsForValue().get(key);
            //缓存中获取不到，查询数据库，并保存在缓存中
            if(Objects.isEmpty(record)) {
                record = mTaskInfoMapper.findRecordByIndex(getTableName(), ind);
                if(Objects.isEmpty(record)) {
                    logger.error("根据索引号查询记录ind{}，无数据",ind);
                    return null;
                }
                redisTemplate.opsForValue().set(key, record);
            }
        } catch (Exception e) {
            logger.error("根据索引号查询记录ind{}报错",ind,e);
        }
        return record;
    }

    @Override
    public TaskInfo findRecordByTaskId(String id) {
        if(StringUtils.isEmpty(id)) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "id为空，无法查询");
            return null;
        }
        TaskInfo record = null;
        try {
            String key = RediskeyConstants.TASKS_INFO_KEY + id ;
            record = (TaskInfo) redisTemplate.opsForValue().get(key);
            //缓存中获取不到，查询数据库，并保存在缓存中
            if(Objects.isEmpty(record)) {
                record = mTaskInfoMapper.findRecordById(getTableName(), id);
                if(Objects.isEmpty(record)) {
                    logger.error("根据id：{}查询任务记录无数据",id);
                    return null;
                }
                redisTemplate.opsForValue().set(key, record);
            }
        } catch (Exception e) {
            logger.error("根据id：{}查询任务记录报错",id,e);
        }
        return record;
        //return mTaskInfoMapper.findRecordById(getTableName(), id);
    }

    @Override
    public List<TaskInfo> getTaskByIds(List<String> ids) {
        List<TaskInfo> res = new ArrayList<TaskInfo>();
        if(null == ids || ids.isEmpty()) {
            return res;
        }

        List<TaskInfo> tasksInDB = getAllTasks();
        if(!tasksInDB.isEmpty()) {
            for(TaskInfo task : tasksInDB) {
                if(null == task) {
                    continue;
                }

                if(ids.contains(task.getTaskId())) {
                    res.add(task);
                }
            }
        }
        return res;
    }

    @Override
    public boolean updateRecordByIndex(int ind, TaskInfo info) {
        if(ind <= 0) {
            Logger.getAnonymousLogger().log(Level.SEVERE, MessageFormat.format("ind[{0}]无效，无法更新数据", ind));
            return false;
        }

        if(null == info) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "info为null，无法更新数据");
            return false;
        }

        mTaskInfoMapper.updateRecordByInd(getTableName(), ind, info);

        return true;
    }

    @Override
    public boolean updateRecordById(String id, TaskInfo info) {
        if(StringUtils.isEmpty(id)) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "id为空，无法更新数据");
            return false;
        }

        if(null == info) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "info为null，无法更新数据");
            return false;
        }

        mTaskInfoMapper.updateRecordByTaskId(getTableName(), id, info);

        return true;
    }

    @Override
    public List<TaskInfo> getAllTasks() {
		/*List<TaskInfo> res = mTaskInfoMapper.getAllRecords(getTableName());
		if(null == res) {
			res = new ArrayList<TaskInfo>();
		}
		*/
        List<TaskInfo> res = null;
        try {
			/*String taskInfo_key = RediskeyConstants.TASKS_INFO_KEY ;
			res = (List<TaskInfo>) redisTemplate.opsForValue().get(taskInfo_key);*/
			/*String taskInfoStr = (String) redisTemplate.opsForValue().get(taskInfo_key);
			res = JSON.parseObject(taskInfoStr, new TypeReference<List<TaskInfo>>(){});*/
            //缓存中获取不到，查询数据库，并保存在缓存中
		/*	if(res == null || res.size()<0) {*/
            res = mTaskInfoMapper.getAllRecords(getTableName());
            if(res == null || res.size()<0) {
                logger.error("获取所有任务,无数据");
                return new ArrayList<TaskInfo>();
            }
				/*redisTemplate.opsForValue().set(taskInfo_key, res,3600, TimeUnit.SECONDS);
			}*/
        } catch (Exception e) {
            logger.error("获取所有任务报错",e);
        }
        return res;
    }

    @Override
    public List<TaskInfo> getTasksByTaskType(int type) {
        List<TaskInfo> res = null;
        try {
            String taskInfo_key = RediskeyConstants.TASKS_INFO_KEY + type;
            res = (List<TaskInfo>) redisTemplate.opsForValue().get(taskInfo_key);
			/*String taskInfoStr = (String) redisTemplate.opsForValue().get(taskInfo_key);
			res = JSON.parseObject(taskInfoStr, new TypeReference<List<TaskInfo>>(){});*/
            //缓存中获取不到，查询数据库，并保存在缓存中
            if(res == null || res.size()<0) {
                res = mTaskInfoMapper.getTasksByTaskType(getTableName(), type);
                if(res == null || res.size()<0) {
                    logger.error("根据任务类型获取所有任务无数据type:{}",type);
                    return new ArrayList<TaskInfo>();
                }
                redisTemplate.opsForValue().set(taskInfo_key, res,3600, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            logger.error("根据任务类型获取所有任务报错,type:{}",type,e);
        }
		/*List<TaskInfo> res = mTaskInfoMapper.getTasksByTaskType(getTableName(), type);
		if(null == res) {
			res = new ArrayList<TaskInfo>();
		}*/
        return res;
    }

    @Override
    public List<TaskInfo> getTaskListByKeyInfo(String country, String op, int netEnv) {
        List<TaskInfo> res = new ArrayList<TaskInfo>();

        return res;
    }

    @Override
    public boolean recordTaskFinishedCount(String taskId, int count) {
        if(StringUtils.isEmpty(taskId)) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "taskId为空，无法更新任务完成数量！");
            return false;
        }

        mTaskInfoMapper.updateTaskFinishedCount(getTableName(), taskId, count);

        return true;
    }

    private static final String M_TAB_NAME = "tasks_info";


    public String getTableName() {
        return M_TAB_NAME;
    }

    private static final String M_SQL_TAB_CREATE =
            "_id bigint(20) NOT NULL AUTO_INCREMENT, " +
                    "task_id varchar(255) NOT NULL, " +
                    "task_type varchar(127), " +
                    "task_desc varchar(255), " +
                    "task_state int(5) NOT NULL, " +
                    "task_total_count int(10), " +
                    "task_finished_count int(10), " +
                    "task_script_id varchar(255) NOT NULL, " +
                    "for_test int(2) NOT NULL, " +
                    "create_time timestamp NOT NULL, " +
                    "PRIMARY KEY (_id)";


    public String getCreateTableSql() {
        return M_SQL_TAB_CREATE;
    }


    private void delGetAll(){
        String taskInfo_key = RediskeyConstants.TASKS_INFO_KEY ;
        redisTemplate.delete(taskInfo_key);
    }
}
