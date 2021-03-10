package com.example.balikbilgi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends Activity {
    String[] baliklar = {"Uçan Balık", "Uçmayan Balık", "Rus Mersini, Karaca", "Şip Balığı, Uzun Burun", "Mersin Balığı", "Mersin Morinası", "Küçük Kayabalığı", "Küçük Balon Balık", ""};
    TextView tv1;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        tv1 = (TextView) findViewById(R.id.textView1);
        ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, baliklar);
        listView.setAdapter(veriAdaptoru);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                baliklar(i);

            }

            private void baliklar(int index) {
                try {
                    InputStream is = getAssets().open("balikbilgi.xml");

                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(is);

                    Element element = doc.getDocumentElement();
                    element.normalize();

                    NodeList nList = doc.getElementsByTagName("balik");

                    String string = "";

                    Node node = nList.item(index);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;
                        string += "\nTürkçe Adı : " + getValue("turkce_ad", element2) + "\n";
                        string += "İngilizce Adı : " + getValue("ingilizce_ad", element2) + "\n";
                        string += "Latince Adı : " + getValue("latince_ad", element2) + "\n";
                        string += "Sinonim : " + getValue("sinonim", element2) + "\n";
                        string += "Diagnostik Özellikliği : " + getValue("diagnostik_ozellik", element2) + "\n";
                        string += "Ekolojik Özelliği : " + getValue("ekolojik_ozellik", element2) + "\n";
                        string += "Açıklama : " + getValue("aciklama1", element2) + "\n";
                        string += "-----------------------";
                    }

                    Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                    intent.putExtra("balikBilgi", string);
                    startActivity(intent);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            private String getValue(String tag, Element element) {
                NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
                Node node = nodeList.item(0);
                return node.getNodeValue();
            }
        });
    }


}

