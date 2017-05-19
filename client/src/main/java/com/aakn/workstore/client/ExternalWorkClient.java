package com.aakn.workstore.client;

import com.aakn.workstore.client.dto.Works;

import java.net.URI;

public interface ExternalWorkClient {

  Works getWorks(URI uri);
}
