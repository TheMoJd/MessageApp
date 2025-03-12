package com.ubo.tp.message.controller.search;

public interface ISearch {

  void addSearchObserver(ISearchObserver observer);

  void removeSearchObserver(ISearchObserver observer);

  void search(String searchString);

}
