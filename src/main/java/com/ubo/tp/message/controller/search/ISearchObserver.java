package com.ubo.tp.message.controller.search;

import com.ubo.tp.message.datamodel.Message;

public interface ISearchObserver {

  void notifySearch(String searchString);
}
