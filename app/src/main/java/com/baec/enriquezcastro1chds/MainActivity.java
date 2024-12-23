package com.baec.enriquezcastro1chds;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btnsuma, btnfiguras, btnlista, btnbiograf, btnsumweb, btntrinomio;
    TextView txtbiografia;
    EditText edtnumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnsuma = findViewById(R.id.btsuma);
        btnfiguras = findViewById(R.id.btfiguras);
        btnlista = findViewById(R.id.btlista);
        btnbiograf = findViewById(R.id.btbiograf);
        btnsumweb = findViewById(R.id.btsumweb);
        btntrinomio = findViewById(R.id.bttrinomio);
        txtbiografia = findViewById(R.id.tvtexto);
        edtnumero = findViewById(R.id.etnumero);

        btnbiograf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerServicioWeb("http://10.10.26.33:3000/Alexander");
            }
        });

        btnlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerServicioWeb("http://10.10.26.33:3000/nombre");
            }
        });

        btnsumweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerServicioWebSuma("http://10.10.26.33:3000/suma");
            }
        });

        btnsuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = edtnumero.getText().toString();

                // Validar que el usuario haya ingresado un número antes de llamar al servicio
                if (!num.isEmpty()) {
                    obtenerServicioWebSuma("http://10.10.26.33:3000/sumacli/" + num);
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor ingresa un número", Toast.LENGTH_SHORT).show();
                };
            }
        });
        btnfiguras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, FigurasGeometricas.class);
                startActivity(i1);
            }
        });
        btntrinomio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTrinomio();
            }
        });
    }
    private void dialogTrinomio() {

        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.trinomio_input);

        EditText etxvalA = dialog.findViewById(R.id.etvalA);
        EditText etxvalB = dialog.findViewById(R.id.etvalB);
        EditText etxvalC = dialog.findViewById(R.id.etvalC);
        Button btncalculatrinomio = dialog.findViewById(R.id.btcalculatrinomio);

        btncalculatrinomio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valA = etxvalA.getText().toString();
                String valB = etxvalB.getText().toString();
                String valC = etxvalC.getText().toString();
                if (!valA.isEmpty() && !valB.isEmpty() && !valC.isEmpty()) {
                    ServicioWebTrinomio("http://192.168.1.12:3000/trinomioCuadradoPerfecto/" + valA + "/" + valB + "/" + valC);

                    
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Por favor, ingrese un valor", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
    private void obtenerServicioWebSuma(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                txtbiografia.setText("Resultado: "+ response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void obtenerServicioWeb(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                txtbiografia.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void ServicioWebTrinomio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Parsear la respuesta JSON
                    JSONObject jsonObject = new JSONObject(response);

                    // Obtener los valores de área y perímetro
                    String tri = jsonObject.getString("trinomio");
                    String fac = jsonObject.getString("factorizacion");

                    txtbiografia.setText(tri+" = "+fac);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}