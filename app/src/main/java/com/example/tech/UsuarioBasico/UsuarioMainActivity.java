package com.example.tech.UsuarioBasico;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tech.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class UsuarioMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    String UsuarioId;

    ImageView ivFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_perfil, R.id.nav_retos, R.id.nav_agenda, R.id.nav_ajustes)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Foto, Nombre completo y Foto del NavigationHeader.
        final View headerView = navigationView.getHeaderView(0);
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        ivFoto = (ImageView)headerView.findViewById(R.id.ivFoto);

        StorageReference profileRef = storageReference.child("usuarios/" + getUsuarioId() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(UsuarioMainActivity.this).load(uri).into(ivFoto);
            }
        });

        final DocumentReference documentReference = firebaseFirestore.collection("Usuarios").document(getUsuarioId());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                TextView navNombre = (TextView)headerView.findViewById(R.id.tvNombre);
                TextView navEmail = (TextView)headerView.findViewById(R.id.tvEmail);
                navNombre.setText(value.getString("nombre") + " " + value.getString("apellidos"));
                navEmail.setText(value.getString("email"));
            }
        });



//        Bundle bundle = new Bundle();
//        bundle.putString("UserId", UsuarioId);
//        GalleryFragment galleryFragment = new GalleryFragment();
//        galleryFragment.setArguments(bundle);
//        Toast.makeText(UsuarioMainActivity.this, bundle.getString("UserId"), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public String getUsuarioId() {
        UsuarioId = getIntent().getExtras().getString("UsuarioId");
        return UsuarioId;
    }
}