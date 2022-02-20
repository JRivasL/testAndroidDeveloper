package com.rivas.testandroiddeveloper.ui.main.images

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rivas.testandroiddeveloper.AndroidApp
import com.rivas.testandroiddeveloper.R
import com.rivas.testandroiddeveloper.databinding.FragmentImagesBinding
import com.rivas.testandroiddeveloper.ui.main.images.adapter.ImageAdapter
import com.rivas.testandroiddeveloper.utils.PermissionUtils
import com.rivas.testandroiddeveloper.utils.extensions.observer
import dagger.android.support.AndroidSupportInjection
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ImagesFragment : Fragment() {

    @Inject
    lateinit var imagesViewModel: ImagesViewModel

    private val listImagesToSend = ArrayList<Uri>()
    private var _binding: FragmentImagesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ImageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentImagesBinding.inflate(inflater, container, false)
        setupTitle()
        setupList()
        setupButtons()
        initObservers()
        return binding.root
    }

    private fun initObservers() {
        imagesObserver()
        observerError()
    }

    private fun imagesObserver() {
        imagesViewModel.images.observer(viewLifecycleOwner, {
            listImagesToSend.remove(it)
            uploadAdapter()
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    private fun setupButtons() {
        binding.btnPicture.setOnClickListener {
            if (ContextCompat.checkSelfPermission(AndroidApp.appContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                validatePermissionsCamera()
            }
        }

        binding.btnGallery.setOnClickListener {
            if (ContextCompat.checkSelfPermission(AndroidApp.appContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                validatePermissionsStorage()
            }
        }

        binding.btnUpload.setOnClickListener {
            imagesViewModel.uploadImages(listImagesToSend)
        }
    }

    private fun setupList() {
        adapter = ImageAdapter()
        binding.rvImages.adapter = adapter
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, TAKE_PICTURE)
    }

    private fun setupTitle() {
        requireActivity().setTitle(R.string.title_images)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            val photo:Bitmap = data!!.extras!!.get("data") as Bitmap
            listImagesToSend.add(getImageUri(AndroidApp.appContext, photo)!!)
            uploadAdapter()
        } else if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
            val selectedImage: Uri? = data!!.data
            listImagesToSend.add(selectedImage!!)
            uploadAdapter()
        }
    }

    private fun uploadAdapter() {
        adapter.setUris(listImagesToSend)
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, System.currentTimeMillis().toString(), null)
        return Uri.parse(path)
    }

    private fun validatePermissionsCamera() {
        if (ContextCompat.checkSelfPermission(AndroidApp.appContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            PermissionUtils.requestPermission(
                activity!!, CAMERA,
                Manifest.permission.CAMERA, true
            )
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, SELECT_PICTURE)
    }

    private fun observerError() {
        imagesViewModel._error.observer(viewLifecycleOwner, {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(it.localizedMessage)
                .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
            builder.create()
            builder.show()
        })
    }

    private fun validatePermissionsStorage() {
        if (ContextCompat.checkSelfPermission(AndroidApp.appContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            PermissionUtils.requestPermission(
                activity!!, STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, true
            )
        }
    }

    companion object {
        const val CAMERA = 1
        const val TAKE_PICTURE = 4
        const val SELECT_PICTURE = 3
        const val STORAGE = 2
    }
}