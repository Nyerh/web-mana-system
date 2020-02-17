package com.weblite.webmanasystem.utils;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Collection;
import java.util.Set;

/**
 * SlSession
 * https://gitee.com/nmwork/RedisUtil
 */
public interface RedisHelper {
    //
    boolean set(String kye, Object object);

    Object get(String key);

    //添加元素,有序集合是按照元素的score值由小到大排列
    Boolean zAdd(String key, String value, double score);

    //批量添加
    Long zAdd(String key, Set<ZSetOperations.TypedTuple<String>> values);
    //TypedTuple使用
    // ZSetOperations.TypedTuple<String> objectTypedTuple1 = new DefaultTypedTuple<String>(value, score);

    //获取集合的元素, 从小到大排序, start开始位置, end结束位置
    Set<String> zRange(String key, long start, long end);

    //获取集合元素, 并且把score值也获取
    Set<ZSetOperations.TypedTuple<String>> zRangeWithScores(String key, long start, long end);

    //根据Score值查询集合元素的值, 从小到大排序
    Set<String> zRangeByScore(String key, double min, double max);

    //根据Score值查询集合元素, 从小到大排序
    Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max);

    //根据Score值查询集合元素, 从小到大排序
    Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max, long start, long end);

//----------------------------------------------------------------------------------

    //获取集合的元素, 从大到小排序
    Set<String> zReverseRange(String key, long start, long end);

    //获取集合的元素, 从大到小排序, 并返回score值
    Set<ZSetOperations.TypedTuple<String>> zReverseRangeWithScores(String key, long start, long end);

    //根据Score值查询集合元素, 从大到小排序
    Set<String> zReverseRangeByScore(String key, double min, double max);

    //根据Score值查询集合元素, 从大到小排序
    Set<ZSetOperations.TypedTuple<String>> zReverseRangeByScoreWithScores(String key, double min, double max);

    //
    Set<String> zReverseRangeByScore(String key, double min, double max, long start, long end);

//-----------------------------------------------------------------------------------

    //返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
    Long zRank(String key, Object value);

    //返回元素在集合的排名,按元素的score值由大到小排列
    Long zReverseRank(String key, Object value);

    //根据score值获取集合元素数量
    Long zCount(String key, double min, double max);

    //获取集合大小
    Long zSize(String key);

    //获取集合大小
    Long zZCard(String key);

    //获取集合中value元素的score值
    Double zScore(String key, Object value);

//------------------------------------------------------------------------------------

    //获取key和otherKey的并集并存储在destKey中
    Long zUnionAndStore(String key, String otherKey, String destKey);

    //获取key和多个集合的并集并存储在destKey中
    Long zUnionAndStore(String key, Collection<String> otherKeys, String destKey);

//-----------------------------------------------------------------------------------

    //获取key和otherKey的交集并存储在destKey中
    Long zIntersectAndStore(String key, String otherKey, String destKey);

    //获取key和多个集合的交集并存储在destKey中
    Long zIntersectAndStore(String key, Collection<String> otherKeys, String destKey);

    //-----------------------------------------------------------------------------------
    //使用迭代器获取
    Cursor<ZSetOperations.TypedTuple<String>> zScan(String key, ScanOptions options);

}
