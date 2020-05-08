package com.medco.mymedicallog.ui.interfaces.messages;

import com.rabbitmq.client.DeliverCallback;

public interface OnMessageReceivedListener {
    DeliverCallback deliverCallback(Object consumerTag, Object delivery);
}
