package com.ubo.tp.message.controller.search;

public interface ISearch {

  void addSearchObserver(ISearchObserver observer);

  void removeSearchObserver(ISearchObserver observer);

  public void search(String searchString);

}
