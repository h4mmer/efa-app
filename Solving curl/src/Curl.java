
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Curl {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			post();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void post() throws IOException {

		String post = "date=1375480156&mail=&dfBoot=Test&cmbBootTyp=K1&dfSteuermann=&dfName1=test&dfName2=&dfName3=&dfName4=&dfName5=&dfName6=&dfName7=&dfName8=&dfName9=&dfName10=&dfName11=&dfName12=&dfName13=&dfName14=&dfName15=&dfName16=&dfName17=&dfName18=&dfName19=&dfName20=&dfName21=&dfName22=&dfName23=&dfName24=&cmbTrommler=&dfDatum=0001-01-01&dfStartZeit=02%3A02&dfEndeZeit=23%3A03&dfStrecke=sa&dfkm=0&cmbFahrtart=Wanderfahrt&dfBemerkung=TEST";
		URL url = new URL("http://efa.kieler-kanu-klub.de/index.php"+"?"+post);
		// URL url = new URL("http://127.0.0.1/index.php");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(post.toString().getBytes().length));
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.connect();
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			out.writeBytes(post.toString());
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream(), "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			out.writeBytes(post.toString());
			out.flush();
			out.close();
			System.out.println("_________________________________________________________");
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

		} finally {
			connection.disconnect();
		}
	}
}
