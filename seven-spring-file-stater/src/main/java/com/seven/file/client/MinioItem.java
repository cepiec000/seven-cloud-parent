package com.seven.file.client;

import io.minio.messages.Item;

public class MinioItem {
    private Item item;
    public MinioItem(Item item) {
        this.item=item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
