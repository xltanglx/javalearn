package designpattern.observer.improve;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 创建一个WeatherData
		WeatherData weatherData = new WeatherData();
		
		// 创建观察者
		CurrentConditions currentConditions = new CurrentConditions();
		BaiduSite baiduSite = new BaiduSite();

		// 注册观察者到weatherData
		weatherData.registerObserver(currentConditions);
		weatherData.registerObserver(baiduSite);
		
		// 测试
		System.out.println("通知各个注册的观察者, 看看信息");
		weatherData.setData(10f, 100f, 30.3f);

		// 移除某个观察者
		weatherData.removeObserver(currentConditions);

		// 再次测试
		System.out.println();
		System.out.println("通知各个注册的观察者, 看看信息");
		weatherData.setData(10f, 100f, 30.3f);
	}

}
