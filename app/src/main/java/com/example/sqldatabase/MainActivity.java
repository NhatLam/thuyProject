package com.example.sqldatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database;

    ListView lvBan;
    ArrayList<DSBan> arrayDSBan;
    DSBanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvBan = (ListView) findViewById(R.id.listviewBan);
        arrayDSBan = new ArrayList<>();

        adapter = new DSBanAdapter(this, R.layout.dong_dsban, arrayDSBan);
        lvBan.setAdapter(adapter);

        // tạo database bàn
        database = new Database(this, "ban.sqlite", null, 1);

        // tạo bảng danh sách bàn
        database.QueryData("CREATE TABLE IF NOT EXISTS DSBan(IdBan INTEGER PRIMARY KEY AUTOINCREMENT, TenBan VARCHAR(50))");

        // insert data
 //       database.QueryData("INSERT INTO DSBan VALUES(null, 'Bàn 1')");

        GetDataDSBan();
    }

    public void DialogXoaBan(String tenban, int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa" + tenban + "không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM DSBan WHERE IdBan = '"+ id +"'");
                Toast.makeText(MainActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                GetDataDSBan();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialogXoa.show();
    }

    public void DialogSuaBan(String ten, int id){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua_ban);

        EditText edtTenBan = (EditText) dialog.findViewById(R.id.editTextTenBanEdit);
        Button btnXacNhan = (Button) dialog.findViewById(R.id.buttonXacnhan);
        Button btnHuy = (Button) dialog.findViewById(R.id.buttonHuyEdit);

        edtTenBan.setText(ten);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = edtTenBan.getText().toString().trim();
                database.QueryData("UPDATE DSBan SET TenBan = '"+ tenMoi +"' WHERE IdBan = '"+ id +"' ");
                Toast.makeText(MainActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataDSBan();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void GetDataDSBan(){
        // select data
        Cursor dataDSBan = database.GetData("SELECT * FROM DSBan");
        arrayDSBan.clear();
        while (dataDSBan.moveToNext()){
            String tenban = dataDSBan.getString(1);
            int id = dataDSBan.getInt(0);
            arrayDSBan.add(new DSBan(id, tenban));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_ban, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuAdd){
            DialogThem();
        }

        return super.onOptionsItemSelected(item);
    }

    private void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_ban);

        EditText edtTen = (EditText) dialog.findViewById(R.id.editTextTenBan);
        Button btnThem = (Button) dialog.findViewById(R.id.buttonThem);
        Button btnHuy = (Button) dialog.findViewById(R.id.buttonHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenban = edtTen.getText().toString();
                if(tenban.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên bàn!", Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("INSERT INTO DSBan VALUES(null, '"+tenban+"')");
                    Toast.makeText(MainActivity.this, "Đã thêm.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataDSBan();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}