package com.hamster.chat.redis;


import com.oracle.jrockit.jfr.DataType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by Owner on 2018/6/27.
 */
@Slf4j
@Service("redisService")
public class RedisServiceImpl implements RedisService<String, Object> {

    @Resource(name = "redisTemplate")
    protected RedisTemplate redisTemplate;
//    @Resource
//    private SystemCfgService systemCfgService;
//    @Resource
//    private SocketRoomService socketRoomService;
//    @Resource
//    private SocketRoomCfgService socketRoomCfgService;
//    @Resource
//    private TenantService tenantService;
//    @Resource
//    private IpaddressService ipaddressService;
//    @Resource
//    private LotteryTypeService lotteryTypeService;
//    @Resource
//    private SensitiveWordService sensitiveWordService;
//    @Resource
//    private BannedUserService bannedUserService;


    /**
     * 出异常，重复操作的次数
     */
    private static Integer times = 5;

    @Override
    public Set<String> getAllKeys() {
        return redisTemplate.keys("*");
    }


    @Override
    public void addList(String key, List<Object> objectList) {
        for (Object obj : objectList) {
            addList(key, obj);
        }
    }

    @Override
    public long addList(String key, Object obj) {
        return redisTemplate.boundListOps(key).rightPush(obj);
    }

    @Override
    public long addList(String key, Object... obj) {
        return redisTemplate.boundListOps(key).rightPushAll(obj);
    }

    @Override
    public List<Object> getList(String key, long s, long e) {
        return redisTemplate.boundListOps(key).range(s, e);
    }

    @Override
    public List<Object> getList(String key) {
        return redisTemplate.boundListOps(key).range(0, getListSize(key));
    }

    @Override
    public long getListSize(String key) {
        return redisTemplate.boundListOps(key).size();
    }

    @Override
    public long removeListValue(String key, Object object) {
        return redisTemplate.boundListOps(key).remove(0, object);
    }

    @Override
    public long removeListValue(String key, Object... objects) {
        long r = 0;
        for (Object object : objects) {
            r += removeListValue(key, object);
        }
        return r;
    }

    @Override
    public void remove(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                remove(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    @Override
    public void removeBlear(String... blears) {
        for (String blear : blears) {
            removeBlear(blear);
        }
    }

    @Override
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    @Override
    public void removeBlear(String blear) {
        redisTemplate.delete(redisTemplate.keys(blear));
    }

    @Override
    public void removeByRegular(String... blears) {
        for (String blear : blears) {
            removeBlear(blear);
        }
    }

    @Override
    public void removeByRegular(String blear) {
        Set<String> stringSet = getAllKeys();
        for (String s : stringSet) {
            if (Pattern.compile(blear).matcher(s).matches()) {
                redisTemplate.delete(s);
            }
        }
    }

    @Override
    public void removeMapFieldByRegular(String key, String... blears) {
        for (String blear : blears) {
            removeMapFieldByRegular(key, blear);
        }
    }

    @Override
    public void removeMapFieldByRegular(String key, String blear) {
        Map<String, Object> map = getMap(key);
        Set<String> stringSet = map.keySet();
        for (String s : stringSet) {
            if (Pattern.compile(blear).matcher(s).matches()) {
                redisTemplate.boundHashOps(key).delete(s);
            }
        }
    }

    @Override
    public Long removeZSetValue(String key, Object... value) {
        return redisTemplate.boundZSetOps(key).remove(value);
    }

    @Override
    public void removeZSet(String key) {
        removeZSetRange(key, 0L, getZSetSize(key));
    }

    @Override
    public void removeZSetRange(String key, Long start, Long end) {
        redisTemplate.boundZSetOps(key).removeRange(start, end);
    }

    @Override
    public void setZSetUnionAndStore(String key, String key1, String key2) {
        redisTemplate.boundZSetOps(key).unionAndStore(key1, key2);
    }

    @Override
    public Set<Object> getZSetRange(String key) {
        return getZSetRange(key, 0, getZSetSize(key));
    }

    @Override
    public Set<Object> getZSetRange(String key, long s, long e) {
        return redisTemplate.boundZSetOps(key).range(s, e);
    }

    @Override
    public Set<Object> getZSetReverseRange(String key) {
        return getZSetReverseRange(key, 0, getZSetSize(key));
    }

    @Override
    public Set<Object> getZSetReverseRange(String key, long start, long end) {
        return redisTemplate.boundZSetOps(key).reverseRange(start, end);
    }

    @Override
    public Set<Object> getZSetRangeByScore(String key, double start, double end) {
        return redisTemplate.boundZSetOps(key).rangeByScore(start, end);
    }

    @Override
    public Set<Object> getZSetReverseRangeByScore(String key, double start, double end) {
        return redisTemplate.boundZSetOps(key).reverseRangeByScore(start, end);
    }

    @Override
    public long getZSetCountSize(String key, double sMin, double sMax) {
        return redisTemplate.boundZSetOps(key).count(sMin, sMax);
    }

    @Override
    public long getZSetSize(String key) {
        return redisTemplate.boundZSetOps(key).size();
    }

    @Override
    public double getZSetScore(String key, Object value) {
        return redisTemplate.boundZSetOps(key).score(value);
    }

    @Override
    public double incrementZSetScore(String key, Object value, double delta) {
        return redisTemplate.boundZSetOps(key).incrementScore(value, delta);
    }

    @Override
    public Boolean addZSet(String key, double score, Object value) {
        return redisTemplate.boundZSetOps(key).add(value, score);
    }

    @Override
    public Long addZSet(String key, TreeSet<Object> value) {
        return redisTemplate.boundZSetOps(key).add(value);
    }

    @Override
    public List<Object> getList(String key, int size) {
        return redisTemplate.boundListOps(key).range(0, size);
    }

    @Override
    public Boolean addZSet(String key, double[] score, Object[] value) {
        if (score.length != value.length) {
            return false;
        }
        for (int i = 0; i < score.length; i++) {
            if (addZSet(key, score[i], value[i]) == false) {
                return false;
            }
        }
        return true;
    }

//    @Override
//    public void makeTenant() {
//        if (tenantService == null) {
//            tenantService = SpringBeanFactoryUtils.getBean(TenantService.class);
//        }
//        Map<String, Object> tenantMap = new HashMap<>();
//        List<Tenant> tenantList = tenantService.findAllByTenant(null);
//        tenantList.forEach(tenant -> {
//            tenantMap.put(tenant.getTenantCode(), tenant);
//        });
//        remove(ChatConstant.PROJECT_TENANT);
//        addMap(ChatConstant.PROJECT_TENANT, tenantMap);
//    }
//
//    @Override
//    public void makeLotteryType() {
//        if (lotteryTypeService == null) {
//            lotteryTypeService = SpringBeanFactoryUtils.getBean(LotteryTypeService.class);
//        }
//        Set<Object> tenantKeyList = getMapFieldKey(ChatConstant.PROJECT_TENANT);
//        Map<String, Object> lotteryTypeMap = new HashMap<>();
//        tenantKeyList.forEach(tenantKey -> {
//            List<LotteryType> lotteryTypeList = lotteryTypeService.findLotteryTypeByTenantCode(tenantKey.toString());
//            lotteryTypeList.forEach(lotteryType -> {
//                lotteryTypeMap.put(lotteryType.getLotteryCode(), lotteryType);
//            });
//            remove(ChatConstant.PROJECT_LOTTERYTYPE);
//            addMap(ChatConstant.PROJECT_LOTTERYTYPE, lotteryTypeMap);
//        });
//    }
//
//    @Override
//    public void makeIpaddress() {
//        if (ipaddressService == null) {
//            ipaddressService = SpringBeanFactoryUtils.getBean(IpaddressService.class);
//        }
//        Map<String, Object> ipaddressMap = new HashMap<>();
//        List<Ipaddress> ipaddressList = ipaddressService.findAll();
//        ipaddressList.forEach(ipaddress -> {
//            ipaddressMap.put(ipaddress.getAddress(), ipaddress);
//        });
//        remove(ChatConstant.PROJECT_IPADDRESS);
//        addMap(ChatConstant.PROJECT_IPADDRESS, ipaddressMap);
//    }
//
//    @Override
//    public void makeSensitiveWord() {
//        if (sensitiveWordService == null) {
//            sensitiveWordService = SpringBeanFactoryUtils.getBean(SensitiveWordService.class);
//        }
//        Set<Object> tenantKeyList = getMapFieldKey(ChatConstant.PROJECT_TENANT);
//        tenantKeyList.forEach(tenantKey -> {
//            List<String> sensitiveWords = sensitiveWordService.findAllWord(tenantKey.toString());
//            remove(ChatConstant.Project_Sensitive_Word + "_" + tenantKey.toString());
//            addList(ChatConstant.Project_Sensitive_Word + "_" + tenantKey.toString(), sensitiveWords);
//        });
//    }
//
//    @Override
//    public void makeBannedUser() {
//        if (bannedUserService == null) {
//            bannedUserService = SpringBeanFactoryUtils.getBean(BannedUserService.class);
//        }
//        Set<Object> tenantKeyList = getMapFieldKey(ChatConstant.PROJECT_TENANT);
//        tenantKeyList.forEach(tenantKey -> {
//            List<String> bannedUsers = bannedUserService.findAllByTenantCode(tenantKey.toString());
//            remove(ChatConstant.PROJECT_BANNED_USER + "_" + tenantKey.toString());
//            addList(ChatConstant.PROJECT_BANNED_USER + "_" + tenantKey.toString(), bannedUsers);
//        });
//    }
//
//    @Override
//    public void makeBannedUserByTenantCode(String tenantCode) {
//        if (bannedUserService == null) {
//            bannedUserService = SpringBeanFactoryUtils.getBean(BannedUserService.class);
//        }
//        List<String> bannedUsers = bannedUserService.findAllByTenantCode(tenantCode);
//        remove(ChatConstant.PROJECT_BANNED_USER + "_" + tenantCode);
//        addList(ChatConstant.PROJECT_BANNED_USER + "_" + tenantCode, bannedUsers);
//    }
//
//    @Override
//    public void redisRefreshC() {
//        redisTemplate.delete(getAllKeys());
//    }
//
//    @Override
//    public void makeSystemCfg() {
//        if (systemCfgService == null) {
//            systemCfgService = SpringBeanFactoryUtils.getBean(SystemCfgService.class);
//        }
//        Set<Object> tenantKeyList = getMapFieldKey(ChatConstant.PROJECT_TENANT);
//        tenantKeyList.forEach(tenantKey -> {
//            Map<String, Object> systemCfgMap = new HashMap<>();
//            SystemCfg systemCfgQuery = new SystemCfg();
//            systemCfgQuery.setTenantCode(tenantKey.toString());
//            List<SystemCfg> systemCfgs = systemCfgService.findAllCfg(systemCfgQuery);
//            systemCfgs.forEach(systemCfg -> {
//                systemCfgMap.put(systemCfg.getCode(), systemCfg.getValue());
//                addMap(ChatConstant.PROJECT_SYSTEM_CFG + "_" + tenantKey.toString(), systemCfgMap);
//            });
//        });
//    }
//
//    @Override
//    public void makeSocketRoom() {
//        if (socketRoomService == null) {
//            socketRoomService = SpringBeanFactoryUtils.getBean(SocketRoomService.class);
//        }
//        if (socketRoomCfgService == null) {
//            socketRoomCfgService = SpringBeanFactoryUtils.getBean(SocketRoomCfgService.class);
//        }
//        Set<Object> tenantKeyList = getMapFieldKey(ChatConstant.PROJECT_TENANT);
//        tenantKeyList.forEach(tenantKey -> {
//            List<SocketRoom> socketRooms = socketRoomService.getChatRoom(tenantKey.toString());
//            Map<String, Object> socketRoomMap = new HashMap<>();
//            Map<String, Object> socketRCMap = new HashMap();
//            socketRooms.forEach(socketRoom -> {
//                if (socketRoom.getStatus().equals(SocketRoomStatus.正常)) {
//                    socketRoomMap.put(socketRoom.getRoomId(), socketRoom);
//                    List<SocketRoomCfg> socketRoomCfgs = socketRoomCfgService.findCfgByRoomId(socketRoom.getRoomId());
//                    Map<String, Object> socketRoomCfgMap = new HashMap<>();
//                    socketRoomCfgs.forEach(socketRoomCfg -> {
//                        socketRoomCfgMap.put(socketRoomCfg.getCode(), socketRoomCfg.getValue());
//                    });
//                    socketRCMap.put(socketRoom.getRoomId(), socketRoomCfgMap);
//                }
//            });
//            if (socketRoomMap != null)
//                addMap(ChatConstant.PROJECT_SOCKET_ROOM + "_" + tenantKey.toString(), socketRoomMap);
//            if (socketRCMap != null)
//                addMap(ChatConstant.PROJECT_SOCKET_ROOM_CFG + "_" + tenantKey.toString(), socketRCMap);
//        });
//    }


    @Override
    public void remove(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public void removeZSetRangeByScore(String key, double s, double e) {
        redisTemplate.boundZSetOps(key).removeRangeByScore(s, e);
    }

    @Override
    public Boolean setSetExpireTime(String key, Long time) {
        return redisTemplate.boundSetOps(key).expire(time, TimeUnit.SECONDS);
    }

    @Override
    public Boolean setZSetExpireTime(String key, Long time) {
        return redisTemplate.boundZSetOps(key).expire(time, TimeUnit.SECONDS);
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public Object get(int key) {
        return this.get(String.valueOf(key));
    }

    public Object get(long key) {
        return this.get(String.valueOf(key));
    }

    public Object get(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    @Override
    public List<Object> get(String... keys) {
        List<Object> list = new ArrayList<Object>();
        for (String key : keys) {
            list.add(get(key));
        }
        return list;
    }



    public void set(long key, Object value) {
        this.set(String.valueOf(key), value);
    }

    public void set(int key, Object value) {
        this.set(String.valueOf(key), value);
    }

    public void set(String key, Object value) {
        redisTemplate.boundValueOps(key).set(value);
    }


    public void set(String key, Object value, Long expireTime) {
        redisTemplate.boundValueOps(key).set(value, expireTime, TimeUnit.SECONDS);
    }


    public boolean setExpireTime(String key, Long expireTime) {
        return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }


    public void removeMapField(String key, Object... field) {
        redisTemplate.boundHashOps(key).delete(field);
    }


    public Long getMapSize(String key) {
        return redisTemplate.boundHashOps(key).size();
    }


    public Map<String, Object> getMap(String key) {
        return redisTemplate.boundHashOps(key).entries();
    }


    public <T> T getMapField(String key, String field) {
        return (T) redisTemplate.boundHashOps(key).get(field);
    }

    @Override
    public String getMapFieldToString(String key, String field) {
        Object val = getMapField(key, field);
        if (val == null) {
            return null;
        }
        return val.toString();
    }


    public Boolean hasMapKey(String key, String field) {
        return redisTemplate.boundHashOps(key).hasKey(field);
    }


    public List<Object> getMapFieldValue(String key) {
        return redisTemplate.boundHashOps(key).values();
    }


    public Set<Object> getMapFieldKey(String key) {
        return redisTemplate.boundHashOps(key).keys();
    }


    public void addMap(String key, Map<String, Object> map) {
        redisTemplate.boundHashOps(key).putAll(map);
    }


    public void addMap(String key, String field, Object value) {
        redisTemplate.boundHashOps(key).put(field, value);
    }


    public void addMap(String key, String field, Object value, long time) {
        redisTemplate.boundHashOps(key).put(field, value);
        redisTemplate.boundHashOps(key).expire(time, TimeUnit.SECONDS);
    }


    public void watch(String key) {
        redisTemplate.watch(key);
    }


    public void addSet(String key, Object... obj) {
        redisTemplate.boundSetOps(key).add(obj);
    }


    public long removeSetValue(String key, Object obj) {
        return redisTemplate.boundSetOps(key).remove(obj);
    }


    public long removeSetValue(String key, Object... obj) {
        if (obj != null && obj.length > 0) {
            return redisTemplate.boundSetOps(key).remove(obj);
        }
        return 0L;
    }


    public long getSetSize(String key) {
        return redisTemplate.boundSetOps(key).size();
    }


    public Boolean hasSetValue(String key, Object obj) {
        Boolean boo = null;
        int t = 0;
        while (true) {
            try {
                boo = redisTemplate.boundSetOps(key).isMember(obj);
                break;
            } catch (Exception e) {
                log.error("key[" + key + "],obj[" + obj + "]判断Set中的值是否存在失败,异常信息:" + e.getMessage());
                t++;
            }
            if (t > times) {
                break;
            }
        }
        log.info("key[" + key + "],obj[" + obj + "]是否存在,boo:" + boo);
        return boo;
    }


    public Set<Object> getSet(String key) {
        return redisTemplate.boundSetOps(key).members();
    }


    public Set<Object> getSetUnion(String key, String otherKey) {
        return redisTemplate.boundSetOps(key).union(otherKey);
    }


    public Set<Object> getSetUnion(String key, Set<Object> set) {
        return redisTemplate.boundSetOps(key).union(set);
    }


    public Set<Object> getSetIntersect(String key, String otherKey) {
        return redisTemplate.boundSetOps(key).intersect(otherKey);
    }


    public Set<Object> getSetIntersect(String key, Set<Object> set) {
        return redisTemplate.boundSetOps(key).intersect(set);
    }

}
