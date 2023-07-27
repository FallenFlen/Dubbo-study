package com.flz.mydubbo.businessservice.filter;

import com.flz.mydubbo.common.constant.Constants;
import com.flz.mydubbo.common.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.MDC;

@Slf4j
@Activate(group = "consumer", order = -3000)
public class ConsumerTraceFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 取出traceId并发送
        String traceId = MDC.get(Constants.TRACE_ID);
        log.info("current trace id:{}", traceId);
        if (StringUtils.isEmpty(traceId)) {
            traceId = RandomUtils.uuid32();
            MDC.put(Constants.TRACE_ID, traceId);
            log.info("reset trace id:{}", traceId);
        }
        RpcContext.getClientAttachment().setAttachment(Constants.TRACE_ID, traceId);
        return invoker.invoke(invocation);
    }
}
