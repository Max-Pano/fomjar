package com.ski.fbbp;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ski.fbbp.monitor.OrderMonitor;
import com.ski.fbbp.session.SessionReturn;

import fomjar.server.FjMessageWrapper;
import fomjar.server.FjServer;
import fomjar.server.FjServer.FjServerTask;
import fomjar.server.msg.FjDscpMessage;
import fomjar.server.msg.FjMessage;
import fomjar.server.session.FjSessionController;
import fomjar.server.session.FjSessionNotMatchException;

public class FbbpTask implements FjServerTask {
    
    private static final Logger logger = Logger.getLogger(FbbpTask.class);
    
    private List<FjSessionController> scs;
    
    public FbbpTask(String serverName) {
        scs = new LinkedList<FjSessionController>();
        scs.add(new SessionReturn());
        new OrderMonitor(serverName).start();
    }

    @Override
    public void onMessage(FjServer server, FjMessageWrapper wrapper) {
        FjMessage msg = wrapper.message();
        if (!(msg instanceof FjDscpMessage)) {
            logger.error("unsupported format message, raw data:\n" + wrapper.attachment("raw"));
            return;
        }
        
        FjDscpMessage dmsg = (FjDscpMessage) msg;
        try {FjSessionController.dispatch(server, scs, dmsg);} // 通用会话消息
        catch (FjSessionNotMatchException e) {logger.error("dispatch failed for message: " + msg, e);}
    }

}
