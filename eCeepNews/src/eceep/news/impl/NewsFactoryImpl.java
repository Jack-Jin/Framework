package eceep.news.impl;

import eceep.news.News;
import eceep.news.NewsFactory;

public class NewsFactoryImpl implements NewsFactory {
	private static NewsFactory factory;
	
	public static News getInstance() {
		if(factory == null){
			factory = new NewsFactoryImpl();
		}
		
		return factory.getNews();
	}
	
	
	@Override
	public News getNews() {
		return new NewsService();
	}

}
