public class Client {
    //variables for each new Client
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean connected;
    private AsyncTask ct;

    public Client() {
        clientSocket = null;
        out = null;
        in = null;
        connected = false;
    }

    public void connect(Context context, String host, int port) {
        ct = new ConnectTask(context).execute(host, String.valueOf(port));
    }    
    private class ConnectTask extends AsyncTask<String, String, Void> {
        private Context context;
        private String host;
        private int port;

        public ConnectTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String host = params[0];
                int port = Integer.parseInt(params[1]);
                clientSocket = new Socket(InetAddress.getByName(host), port);
                out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
            } catch (UnknownHostException e) {
                makeToast("Don't know about host: " + host + ":" + port);
                Log.e("ERROR", e.getMessage());
            } catch (IOException e) {
                makeToast("Couldn't get I/O for the connection to: " + host + ":" + port);
                Log.e("ERROR", e.getMessage());
            }
            connected = true;
            publishProgress("connect");
            return null;
        }
        @Override
        protected void onProgressUpdate(String...text){
            if(connected) {
                try {
                    while (!in.readLine().equalsIgnoreCase("Disconnecting")) {
                        sendMessage(text[0]);
                    }
                    disconnect(context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        @Override
        protected void onPreExecute() {
            makeToast("Connecting..");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            if (connected) {
                makeToast("Connection successful");
            }
            super.onPostExecute(result);
        }


    }
        public void disconnect(Context context) {
        if (connected) {
            try {
                in.close();
//                out.println("Disconnect\n");
                out.close();
                clientSocket.close();
                connected = false;
            } catch (IOException e) {
                makeToast("Couldn't get I/O for the connection");
                Log.e("ERROR", e.getMessage());
            }
        }
    }
        public void sendMessage(String command){
        if ( connected ) this.out.println(command);
    }
}