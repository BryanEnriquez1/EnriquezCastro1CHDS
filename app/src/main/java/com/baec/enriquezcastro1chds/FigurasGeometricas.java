package com.baec.enriquezcastro1chds;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class FigurasGeometricas extends AppCompatActivity {

    Button btntrianrect, btnrombo, btnparalelogramo, btnregreso;
    TextView tvxarea, tvxperimetro;
    Drawable trianguloRectF, romboF, paralelogramF;
    ImageView ivfiguras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_figuras_geometricas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btntrianrect = findViewById(R.id.bttrirec);
        btnrombo = findViewById(R.id.btrombo);
        btnparalelogramo = findViewById(R.id.btparalelogramo);
        btnregreso = findViewById(R.id.btregresar);
        tvxarea = findViewById(R.id.tvarea);
        tvxperimetro = findViewById(R.id.tvperimetro);
        ivfiguras = findViewById(R.id.ivfigura);
        trianguloRectF = getResources().getDrawable(R.drawable.triangulo_rectangulo);
        trianguloRectF.setBounds(0, 0, 16, 16);
        romboF = getResources().getDrawable(R.drawable.rombo);
        romboF.setBounds(0, 0, 16, 16);
        paralelogramF = getResources().getDrawable(R.drawable.paralelogram);
        paralelogramF.setBounds(0, 0, 16, 16);

        btntrianrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTrianguloRect();
            }
        });
        btnrombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRombo();
            }
        });
        btnparalelogramo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogParalelogramo();
            }
        });
        btnregreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void dialogTrianguloRect() {

        Dialog dialog = new Dialog(FigurasGeometricas.this);
        dialog.setContentView(R.layout.triangulo_rectangulo_input);

        EditText etxbase = dialog.findViewById(R.id.etbase);
        EditText etxaltura = dialog.findViewById(R.id.etaltura);
        Button btncalculatrirec = dialog.findViewById(R.id.btcalculatrirec);

        btncalculatrirec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valbase = etxbase.getText().toString();
                String valaltura = etxaltura.getText().toString();
                if (!valbase.isEmpty() && !valaltura.isEmpty()) {
                    ServicioWebFiguras("http://192.168.1.5:3000/trianguloRectangulo/" + valbase + "/" + valaltura);
                    ivfiguras.setImageDrawable(trianguloRectF);
                    dialog.dismiss();
                } else {
                    Toast.makeText(FigurasGeometricas.this, "Por favor, ingrese un valor", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
    private void dialogRombo() {

        Dialog dialog = new Dialog(FigurasGeometricas.this);
        dialog.setContentView(R.layout.rombo_input);

        EditText etxdmayor = dialog.findViewById(R.id.etdmayor);
        EditText etxdmenor = dialog.findViewById(R.id.etdmenor);
        EditText etxlado = dialog.findViewById(R.id.etlado);
        Button btncalcularombo = dialog.findViewById(R.id.btcalcularombo);

        btncalcularombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valdmayor = etxdmayor.getText().toString();
                String valdmenor = etxdmenor.getText().toString();
                String vallado = etxlado.getText().toString();
                if (!valdmayor.isEmpty() && !valdmenor.isEmpty() && !vallado.isEmpty()) {
                    ServicioWebFiguras("http://192.168.1.5:3000/rombo/" + valdmayor + "/" + valdmenor +"/"+ vallado);
                    ivfiguras.setImageDrawable(romboF);
                    dialog.dismiss();
                } else {
                    Toast.makeText(FigurasGeometricas.this, "Por favor, ingrese un valor", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
    private void dialogParalelogramo() {

        Dialog dialog = new Dialog(FigurasGeometricas.this);
        dialog.setContentView(R.layout.paralelogram_input);

        EditText etxbasep = dialog.findViewById(R.id.etbasep);
        EditText etxalturap = dialog.findViewById(R.id.etalturap);
        EditText etxladop = dialog.findViewById(R.id.etladop);
        Button btncalculaparalelogram = dialog.findViewById(R.id.btcalculaparalelogram);

        btncalculaparalelogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valbasep = etxbasep.getText().toString();
                String valalturap = etxalturap.getText().toString();
                String valladop = etxladop.getText().toString();
                if (!valbasep.isEmpty() && !valalturap.isEmpty() && !valladop.isEmpty()) {
                    ServicioWebFiguras("http://192.168.1.5:3000/paralelogramo/" + valbasep + "/" + valalturap +"/"+ valladop);
                    ivfiguras.setImageDrawable(paralelogramF);
                    dialog.dismiss();
                } else {
                    Toast.makeText(FigurasGeometricas.this, "Por favor, ingrese un valor", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
    private void ServicioWebFiguras(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Parsear la respuesta JSON
                    JSONObject jsonObject = new JSONObject(response);

                    // Obtener los valores de área y perímetro
                    String area = jsonObject.getString("area");
                    String perimetro = jsonObject.getString("perimetro");

                    tvxarea.setText(area);
                    tvxperimetro.setText(perimetro);

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