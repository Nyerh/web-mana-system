package com.weblite.webmanasystem.utils;

/**
 * @author ：Beatrice
 * @date ：Created in 2020/3/24 14:55
 * @Description:JAVA版BV转AV测试Demo1
 */
public class TranseUtil {

    public  String test="fZodR9XQDSUm21yCkr6zBqiveYah8bt4xsWpHnJE7jL5VG3guMTKNPAwcF";
    public  int[] tr=new int[256];
    public  int[] s=new int[]{9,8,1,6,2,4};
    public  long xor=177451812L;
    public  long add=8728348608L;

    public TranseUtil() {
       for (int i=0;i<58;i++)
       {
           char index = test.charAt(i);
           tr[index]=i;
       }
    }

    //作用方法
    public  Long transeBVToAv(String bvId)
    {
        bvId=bvId.substring(2);
        long r=0;
        for (int i=0;i<6;i++)
        {
            char c = bvId.charAt(s[i]);
            r+=tr[c]*Math.pow(58,i);
        }
        return (r-add)^xor;
    }
}
