package com.example.cuahangdienthoai;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ValueEventListener {
    // khai báo 3 gridview dùng chung cho menu Navigation Buttom
    GridView grDanhSachdt;
    GridView grDanhSachdt2;
    GridView grDanhSachdt3;


    DienThoai2 dienThoai2;
    DienThoai1 dienThoai1;
    DienThoai2 sachAdapter3;



    ArrayList<DienThoai>arrayListSach=new ArrayList<DienThoai>();

    //khai báo database firebase và đường dẫn
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //mảng dùng lưu vị trí đăt mua
    List<DienThoai>list=new ArrayList<DienThoai>();


    //danh sach
    ArrayList<DienThoai>arrayListdt2=new ArrayList<DienThoai>();

    ArrayList<DienThoai>arrayListdt3=new ArrayList<DienThoai>();
    //xoa
    ArrayList<DienThoai>arrayListdt4=new ArrayList<DienThoai>();

    // email đăng nhập
    String emailDangNhap="";

    // vị trí xóa trong danh sách dặt
    int position1=0;

    Button btnUser;

    //menu navigation Buttom trong danh sách đặt
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                //menu trang chủ, dùng setContentView() để trở về màn hình các cuốn sách
                case R.id.navigation_home:
                    setContentView(R.layout.activity_main);
                    addControls();
                    addEvents();
                    fakeData();
                    firebaseDatabase=FirebaseDatabase.getInstance();
                    databaseReference=firebaseDatabase.getReference().child("List");
                    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                    return true;
                    //menu danh sách đặt
                case R.id.navigation_dashboard:

                    // gridview 2 dùng chung một gridview cùng id R.id.grDanhSachSach
                    grDanhSachdt2=(GridView)findViewById(R.id.grDanhSachDT);
                    dienThoai2 =new DienThoai2(MainActivity.this,R.layout.itemrow1);
                    dienThoai2.addAll(arrayListdt2);
                    grDanhSachdt2.setAdapter(dienThoai2);
                    grDanhSachdt2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            DienThoai dienThoai = dienThoai2.getItem(position);
                            position1=position;
                            Toast.makeText(MainActivity.this,"So Luong: "+ dienThoai.getSoLuong()+",Dia Chi : "+ dienThoai.getDiaChiMua()
                                    +"Email :"+ dienThoai.getEmailDangNhap()+"vị trí "+position,Toast.LENGTH_LONG).show();

                        }
                    });
                    return true;
                    //menu đăng nhập đăng kí
                case R.id.navigation_notifications:
                    Intent intentChuyeSangDangNhap=new Intent(MainActivity.this,DangNhapActivity.class);
                    startActivity(intentChuyeSangDangNhap);
                    return true;
                    // menu lịch sử xóa
                case R.id.mnu_lichsu:
                    // gridview 3 dùng chung trong Navigation Buttom
                    grDanhSachdt3=(GridView) findViewById(R.id.grDanhSachDT);
                    sachAdapter3 =new DienThoai2(MainActivity.this,R.layout.itemrow1);
                    sachAdapter3.addAll(arrayListdt4);
                    grDanhSachdt3.setAdapter(sachAdapter3);
                    grDanhSachdt3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            DienThoai dienThoai =sachAdapter3.getItem(position);
                            Toast.makeText(MainActivity.this, dienThoai.getTendt()+ dienThoai.getDiaChiMua(),Toast.LENGTH_LONG).show();
                        }
                    });
                return true;
            }
            return false;

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
        fakeData();

        // nếu bật phần mềm khi đó danh sách đặt rỗng, khi đó hiển thị lời chào
        if(list.size()==0) {
            Toast.makeText(MainActivity.this, "Xin chào các bạn!", Toast.LENGTH_SHORT).show();
        }
        // khởi tạo đường dẫn và database
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("List");
        databaseReference.addValueEventListener(this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void fakeData() {

        //thêm các  phần tử đối tượng sách vào Adapter
        //hai lenh nay dung xoa du lieu trong Adapter va mang khi dung setContenView quay tro ve man hinh trang chu
        dienThoai1.clear();
        arrayListSach.clear();
        dienThoai1.add(new DienThoai(1,"Samsung note9","",19000000,"Samsung",0,R.drawable.a1,"",""));
        dienThoai1.add(new DienThoai(2,"Samsung s10","",18500000,"Samsung",0,R.drawable.a2,"",""));
        dienThoai1.add(new DienThoai(3,"Sony a10","",1200000,"Sony",0,R.drawable.a3,"",""));
        dienThoai1.add(new DienThoai(4,"Samsung A20","",3500000,"Samsung",0,R.drawable.a4,"",""));
        dienThoai1.add(new DienThoai(5,"Samsung A50","",5000000,"Samsung",0,R.drawable.a5,"",""));
        dienThoai1.add(new DienThoai(6,"Iphone 11 pro max","",35000000,"Iphone",0,R.drawable.a6,"",""));
        dienThoai1.add(new DienThoai(7,"Iphone 11","",25000000,"Iphone",0,R.drawable.a7,"",""));
        dienThoai1.add(new DienThoai(8,"Samsung A20 xám","",3500000,"Samsung",0,R.drawable.a8,"",""));
       dienThoai1.add(new DienThoai(9,"Oppo nope 8","",1200000,"Oppo",0,R.drawable.a9,"",""));
        dienThoai1.add(new DienThoai(10,"Oppo note 7","",12500000,"Opp0",0,R.drawable.a10,"",""));
        dienThoai1.add(new DienThoai(11,"Oppo note 7 trắng","",12500000,"Oppo",0,R.drawable.a11,"",""));
        dienThoai1.add(new DienThoai(12,"Oppo nope 8 xám","",1200000,"Oppo",0,R.drawable.a12,"",""));
        dienThoai1.add(new DienThoai(13,"Oppo remi 9","",1200000,"Oppo",0,R.drawable.a13,"",""));
        dienThoai1.add(new DienThoai(14,"Oppo remi 9 ","",1200000,"Oppo",0,R.drawable.a14,"",""));
       dienThoai1.add(new DienThoai(15,"Iphone 11 pro max","",3500000,"Iphone",0,R.drawable.a15,"",""));
       // thêm các đối tượng sách vào mảng
       for(int i = 0; i< dienThoai1.getCount(); i++){
            DienThoai dienThoai = dienThoai1.getItem(i);
            arrayListSach.add(dienThoai);
        }
    }

    private void addControls() {

        // gridview 1 dùng chung một gridview cùng id R.id.grDanhSachSach
        grDanhSachdt=(GridView)findViewById(R.id.grDanhSachDT);
        dienThoai1 =new DienThoai1(MainActivity.this,R.layout.itemrow1);
        grDanhSachdt.setAdapter(dienThoai1);
    }

    private void addEvents() {
        grDanhSachdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DienThoai dienThoai = dienThoai1.getItem(position);
                moThongTin(dienThoai);
            }
        });
    }
    // phương thức hiển thị layout chi tiết và đặt mua
    private void moThongTin(final DienThoai dienThoai) {
        setContentView(R.layout.itemrow2);
        final EditText edtDiaChiMua=(EditText) findViewById(R.id.edtDiaChi);
        ImageView imgHinhAnhSach2=(ImageView)findViewById(R.id.imgHinhAnhSach2);
        TextView txtTenSach2=(TextView)findViewById(R.id.txtTenSach2);
        TextView txtGiaSach2=(TextView)findViewById(R.id.txtGiasAch2);
        TextView txtNhaXuatBan2=(TextView)findViewById(R.id.txtNhaXuatBan2);
        TextView txtThongTin2=(TextView)findViewById(R.id.txtGioiThieuSach);
        Button btnMua=(Button)findViewById(R.id.btnMua);
        Button btnTroVe=(Button) findViewById(R.id.btnTroVe);
        
        imgHinhAnhSach2.setImageResource(dienThoai.getHinhanhdt());
        txtTenSach2.setText(dienThoai.getTendt());
        txtGiaSach2.setText(dienThoai.getGiadt()+"VND");
        txtNhaXuatBan2.setText(dienThoai.getNhasanxuat());
        txtThongTin2.setText(dienThoai.getGioithieudt());
        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nếu chưa đặt hàng thêm danh sách đặt để tiếp tục vị trí đặt tiếp theo
                if (list.size() == 0) {
                    list.addAll(arrayListdt3);
                }
                dienThoai.setDiaChiMua(edtDiaChiMua.getText().toString());
                // nhận email từ màn hình dăng nhập
                    Intent intentNhanEmail = getIntent();
                    emailDangNhap = intentNhanEmail.getStringExtra("emailDangNhap");
                dienThoai.setEmailDangNhap(emailDangNhap);
                //nếu có email mới thêm đối tượng sách, nếu không hiển thị thông báo
                    if (emailDangNhap != null) {
                            dienThoai.setSoLuong(dienThoai.getSoLuong() + 1);
                            list.add(dienThoai);
                            Toast.makeText(MainActivity.this,"So Luong"+ dienThoai.getSoLuong(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Bạn Phải Đăng Nhập!", Toast.LENGTH_LONG).show();

                    }
                    // thêm mảng đặt vào cơ sở dữ liệu
                    databaseReference.setValue(list);



            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                addControls();
                addEvents();
                fakeData();
                firebaseDatabase=FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference().child("List");
                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mnu_search,menu);

        MenuItem mnusearch=menu.findItem(R.id.mnu_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(mnusearch);
        MenuItemCompat.setOnActionExpandListener(mnusearch, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<DienThoai>dsTim=new ArrayList<DienThoai>();
                for(DienThoai dienThoai1 :arrayListSach){
                    //tìm theo tên sách
                    if(dienThoai1.getTendt().contains(newText)) {
                        dsTim.add(dienThoai1);
                    }
                }
                    // xóa Adapter danh sách tìm
                    dienThoai1.clear();
                //thêm mảng tìm thấy vào Adapter
                    dienThoai1.addAll(dsTim);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.mnu_thoat:
                // thoát toàn bộ trương trình
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(startMain);
                finish();
                break;
            case R.id.mnu_XoaItem:
                    // lấy vị trí position1 trong Adapter danh sách đặt
                    DienThoai dienThoaiXoa = dienThoai2.getItem(position1);
                    dienThoai2.remove(dienThoaiXoa);
                    // do mảng trong cơ sở dữ liệu firebase lấy theo mảng trong gridview nên có vị trí giống nhau
                    databaseReference.child(dienThoaiXoa.getId() + "").child("tenSach").setValue("Đã xóa!");
                    list.clear();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        // lấy email từ màn hình đăng nhập
        Intent intentNhanEmail=getIntent();
        String emailDangNhap2=intentNhanEmail.getStringExtra("emailDangNhap");
        ArrayList<DienThoai> dienThoai3 =new ArrayList<DienThoai>();
        ArrayList<DienThoai> dienThoai4 =new ArrayList<DienThoai>();
        ArrayList<DienThoai> dienThoai5 =new ArrayList<DienThoai>();
        // các phần tử trong cơ sở dữ liệu firebase và ép kiểu về đối tượng sách ôn thi
        Iterable<DataSnapshot> dataSnapshots=dataSnapshot.getChildren();
        int i=-1;
        for(DataSnapshot dataSnapshot1:dataSnapshots) {
            i=i+1;
            DienThoai dienThoai = dataSnapshot1.getValue(DienThoai.class);
            dienThoai.setId(i);
            dienThoai4.add(dienThoai);
            // nếu email đã có thì mới thêm phần tử sách theo email tương ứng vào danh sách đặt theo user
            if((emailDangNhap2!=null)){
                if((!dienThoai.getTendt().contains("Đã xóa!"))&& dienThoai.getEmailDangNhap().contains(emailDangNhap2)){
                        dienThoai3.add(dienThoai);
                }
                // nếu đã xóa đối tượng thì thêm đối tượng xóa vào lịch sử
                if(dienThoai.getTendt().contains("Đã xóa!")&&(dienThoai.getEmailDangNhap().contains(emailDangNhap2))){
                    dienThoai5.add(dienThoai);
                }

            }

        }
        // thêm danh sách đặt theo user
        arrayListdt2.clear();
        arrayListdt2.addAll(dienThoai3);

        arrayListdt3.clear();
        arrayListdt3.addAll(dienThoai4);
        // thêm danh sách lịch sử xóa theo user
        arrayListdt4.clear();
        arrayListdt4.addAll(dienThoai5);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }
}
