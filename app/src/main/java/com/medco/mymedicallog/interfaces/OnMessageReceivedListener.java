package com.medco.mymedicallog.interfaces;

import com.rabbitmq.client.DeliverCallback;

public interface OnMessageReceivedListener {
    DeliverCallback deliverCallback(Object consumerTag, Object delivery);
}
