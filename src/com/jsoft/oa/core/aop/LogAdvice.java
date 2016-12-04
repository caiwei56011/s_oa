package com.jsoft.oa.core.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 日志切面类
 * @author Administrator
 * @date 2016年8月26日 下午8:56:15 
 * @version V1.0
 */
public class LogAdvice {
	/** 获取日志记录对象 */
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 记录业务层方法的性能(执行时间) 
	 * @param pjp 处理连接点,对应切入点表达式中切入的每个方法,封装了目标方法的信息
	 * @return 返回目标方法的返回值
	 * @throws Throwable
	 */
	public Object around(ProceedingJoinPoint pjp) throws Throwable
	{
		/** 获取当前的系统时间毫秒数 */
		long beginMillis = System.currentTimeMillis();
		logger.info("开始调用【"+ pjp.getSignature().getName() +"】方法，开始时间毫秒数为：" + beginMillis);
		System.out.println("===========调用目标方法之前=============");
		/** 调用目标方法 */
		Object res = pjp.proceed(pjp.getArgs());
		System.out.println("===========调用目标方法之后=============");
		long endMillis = System.currentTimeMillis();
		logger.info("结束调用【"+ pjp.getSignature().getName() +"】方法，结束时间毫秒数为：" + endMillis);
		logger.info("调用【"+ pjp.getSignature().getName() +"】方法，共耗时：" + (endMillis - beginMillis) + "毫秒!");
		logger.info("调用【"+ pjp.getSignature().getName() +"】方法，返回值为：" + res);
		return res;
	}
	/**
	 * 记录业务层方法的异常信息
	 * @param jp 
	 * @param ex
	 */
	public void error(JoinPoint jp, Throwable ex)
	{
		logger.info("调用【"+ jp.getSignature().getName() +"】方法，出现异常！");
		/** 记录异常的自定义提示信息，并记录异常对象的堆栈信息 */
		logger.error(ex.getMessage(), ex);
	}
}
