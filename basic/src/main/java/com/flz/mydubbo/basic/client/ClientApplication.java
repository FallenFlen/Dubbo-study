/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flz.mydubbo.basic.client;

import com.flz.mydubbo.basic.api.GreetingsService;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

import java.io.IOException;

public class ClientApplication {
    private static final String ZOOKEEPER_HOST = "192.168.39.233";
    private static final String ZOOKEEPER_PORT = "2181";
    private static final String ZOOKEEPER_ADDRESS = "zookeeper://" + ZOOKEEPER_HOST + ":" + ZOOKEEPER_PORT;

    public static void main(String[] args) throws IOException {
        // 引用：rpc客户端
        ReferenceConfig<GreetingsService> reference = new ReferenceConfig<>();
        reference.setInterface(GreetingsService.class); // 这里设置接口

        DubboBootstrap.getInstance()
                .application("first-dubbo-consumer") // 应用程序名称
                .registry(new RegistryConfig(ZOOKEEPER_ADDRESS)) // 注册中心实例
                .reference(reference) // rpc client引用
                .start();

        GreetingsService service = reference.get(); // 启动dubbo服务后，获取代理对象
        String message = service.sayHi("dubbo");
        System.out.println("Receive result ======> " + message);
        System.in.read();
        System.exit(0);
    }

}
