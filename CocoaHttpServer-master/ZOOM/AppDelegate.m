//
//  AppDelegate.m
//  ZOOM
//
//  Created by WeShape_Design01 on 16/5/20.
//  Copyright © 2016年 Weshape3D. All rights reserved.
//

#import "AppDelegate.h"
#import "MainViewController.h"

#import "BGLocationConfig.h"

@interface AppDelegate ()
@property (nonatomic, strong) NSTimer *timer;
@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
    
     [self configLocalHttpServer];
    
    MainViewController *mainV = [[MainViewController alloc] init];
  //  UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:mainV];
    mainV.navigationItem.title = @"C-HttpServer";
    
    self.window.rootViewController = mainV;
    
    [self.window makeKeyAndVisible];
    
   // [self performSelector:@selector(configLocalHttpServer) withObject:nil afterDelay:1];
   
    
    // 注意打开Xcode工程里的后台定位和后台刷新功能哦~ 不然会crash
    [BGLocationConfig starBGLocation];
    [self.timer fire];
    
    return YES;
}

#pragma mark -- 本地服务器 --
#pragma mark -服务器
#pragma mark - 搭建本地服务器 并且启动
- (void)configLocalHttpServer{
    _localHttpServer = [[HTTPServer alloc] init];
    [_localHttpServer setType:@"_http.tcp"];
    
    NSFileManager *fileManager = [NSFileManager defaultManager];
    
    NSString *path = webPath;
    XLLog(@">>>>[WebFilePath:]%@",path);
    
    
    if (![fileManager fileExistsAtPath:webPath]){
        XLLog(@">>>> File path error!");
    }else{
        NSString *webLocalPath = webPath;
        [_localHttpServer setDocumentRoot:webLocalPath];
        XLLog(@">>webLocalPath:%@",webLocalPath);
        [self startServer];
    }
}
- (void)startServer
{
    
    NSError *error;
    if([_localHttpServer start:&error]){
        XLLog(@"Started HTTP Server on port %hu", [_localHttpServer listeningPort]);
        self.port = [NSString stringWithFormat:@"%d",[_localHttpServer listeningPort]];
    }
    else{
        XLLog(@"Error starting HTTP Server: %@", error);
    }
}



- (NSTimer *)timer{
    if (nil == _timer) {
        _timer = [NSTimer timerWithTimeInterval:1 repeats:YES block:^(NSTimer * _Nonnull timer) {
            XLLog(@"运行ing");
        }];
        NSRunLoop *curRun = [NSRunLoop currentRunLoop];
        [curRun addTimer:_timer forMode:NSRunLoopCommonModes];
        
    }
    return _timer;
}

- (void)applicationWillResignActive:(UIApplication *)application {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application {
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
