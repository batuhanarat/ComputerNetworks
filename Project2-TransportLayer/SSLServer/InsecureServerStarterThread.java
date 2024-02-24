class InsecureServerStarterThread extends Thread{
    public void run(){
        InsecureServer server = new InsecureServer(InsecureServer.DEFAULT_SERVER_PORT);
    }
}