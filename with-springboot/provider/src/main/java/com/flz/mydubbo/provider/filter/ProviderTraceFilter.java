package com.flz.mydubbo.provider.filter;

import com.flz.mydubbo.common.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.MDC;

@Slf4j
@Activate(group = "provider", order = -3000)
public class ProviderTraceFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 生产者取出traceId
        String traceId = RpcContext.getServerAttachment().getAttachment(Constants.TRACE_ID);
        log.info("consumer trace id:{}", traceId);
        // 设置在MDC中，让生产者impl里也能取到
        MDC.put(Constants.TRACE_ID, traceId);
        return invoker.invoke(invocation);
    }
}
