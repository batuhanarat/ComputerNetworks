class SSLServerStarterThread extends Thread{
    public void run(){
        SSLServer s = new SSLServer(SSLServer.DEFAULT_SSL_SERVER_PORT);
    }
}