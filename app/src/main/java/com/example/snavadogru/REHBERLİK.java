package com.example.snavadogru;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snavadogru.Adaptor.Haber;
import com.example.snavadogru.Adaptor.HaberAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class REHBERLİK extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<Haber> personArrayList;
    HaberAdapter adapter;
    Context  context=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rehberlik);

        //Arkaplanisleri oncreate de execute edilmeli
        //User internet permision unutulmamalı
        new arkaplanisleri().execute();
        rv= (RecyclerView)findViewById(R.id.recyclerview_rehberlik);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        personArrayList = new ArrayList<>();
        //--------------------------------------Silinecek--------------------------------------------------
        Haber p1=new Haber("Afyondan gerekmez insan Selim in mat101 sınavını kaçırdığı dakikalar!","dd","https://www.sabah.com.tr/yasam/2018/06/30/sinavi-1-dakika-ile-kacirdi");
        Haber p2=new Haber("Maraşlı Duayen Rasim ustadan inşşatlarda kullanılacak büyük haber","Rasim","https://www.hurriyet.com.tr/yerel-haberler/kahramanmaras/insaatin-5inci-katinda-tehlikeli-calisma-41121892");
        personArrayList.add(p1);
        personArrayList.add(p2);


        adapter = new HaberAdapter(this,personArrayList);

        rv.setAdapter(adapter);



    }


    private void getHaberfromXml(String UrlString){

        try {

            URL url = new URL(UrlString);
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder dBuilder = dFactory.newDocumentBuilder();

            Document document =  dBuilder.parse(new InputSource(url.openStream()));
            document.getDocumentElement().normalize();
           NodeList nodeList = document.getElementsByTagName("item");



         for(int i=0;i<nodeList.getLength();i++){

                Node node = nodeList.item(i);
                Element elementMain=(Element) node;
                NodeList nodeTitle= elementMain.getElementsByTagName("title");
                NodeList nodeLink = elementMain.getElementsByTagName("atom:link");


                NodeList nodeImage = elementMain.getElementsByTagName("description");

                //title i çekip title a eşitliyoruz
                Element elementTitle=(Element) nodeTitle.item(0);
                String title=elementTitle.getChildNodes().item(0).getNodeValue();


                //link
                //link self-closing tag e sahip tag in attribute a ulaştım
                 Element elementItem = (Element) nodeLink.item(0);
                 String link = elementItem.getAttribute("href");



                //image
                 Element elementImage=(Element) nodeImage.item(0);
                 String description = elementImage.getChildNodes().item(0).getNodeValue();
                 String img="";

                 if(description.contains("<img ")){
                      img = description.substring(description.indexOf("<img "));
                     img= img.substring(img.indexOf("src=")+5);
                     img=img.substring(0,img.lastIndexOf("\""));

                 }



                Haber haber=new Haber(title,img,link);
                personArrayList.add(haber);
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
       catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    public class arkaplanisleri extends AsyncTask<Void,Void,Void>{
        ProgressDialog progressDialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Yükleniyor...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            getHaberfromXml("https://www.milliyet.com.tr/rss/rssnew/egitimrss.xml");
            return null;

        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            HaberAdapter haberAdapter = new HaberAdapter(context,personArrayList);
            rv.setAdapter(haberAdapter);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


    }





}
