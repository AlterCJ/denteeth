package com.example.capstone_apps.views.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.BuildConfig
import com.example.capstone_apps.R
import com.example.capstone_apps.databinding.ContentMainPredictionBinding
import com.example.capstone_apps.databinding.FragmentCameraBinding
import com.example.capstone_apps.helper.YuvToRgbConverter
import com.example.capstone_apps.ml.Model1
import com.example.capstone_apps.viewmodels.HistoryViewModel
import com.example.capstone_apps.viewmodels.PreferenceViewModel
import com.example.capstone_apps.viewmodels.ViewModelFactory
import org.tensorflow.lite.DataType
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.model.Model
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment() {

  private lateinit var binding: FragmentCameraBinding
  private lateinit var contentMainBinding: ContentMainPredictionBinding
  private lateinit var cameraExecutor: ExecutorService
  private lateinit var historyViewModel: HistoryViewModel
  private lateinit var preferenceViewModel: PreferenceViewModel


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.fragment_camera, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentCameraBinding.bind(view)
    contentMainBinding = binding.contentRecognitionResult

    if (allPermissionsGranted()) {
      startCamera()
    } else {
      ActivityCompat.requestPermissions(
        requireActivity(),
        REQUIRED_PERMISSIONS,
        REQUEST_CODE_PERMISSIONS
      )
    }

    cameraExecutor = Executors.newSingleThreadExecutor()
  }

  private fun startCamera() {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())

    cameraProviderFuture.addListener({
      val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

      val preview = Preview.Builder()
        .build()
        .also { it.setSurfaceProvider(binding.viewFinder.surfaceProvider) }

      val imageAnalyzer = ImageAnalysis.Builder()
        .setTargetResolution(Size(224, 224))
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
        .also { analysisUseCase: ImageAnalysis ->
          analysisUseCase.setAnalyzer(
            cameraExecutor,
            ImageAnalyzer(requireActivity(), contentMainBinding)
          )
        }

      val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

      try {
        cameraProvider.unbindAll()

        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)
      } catch (e: Exception) {
        Log.e(TAG, "Use case binding failed", e)
      }
    }, ContextCompat.getMainExecutor(requireActivity()))
  }

  //image analize refrensi code dari mentor
  class ImageAnalyzer(
    ctx: Context,
    private val binding: ContentMainPredictionBinding
  ) :
    ImageAnalysis.Analyzer {

    private val finalModel: Model1 by lazy {
      // TODO 6. Optional GPU acceleration
      val compatList = CompatibilityList()

      val options = if (compatList.isDelegateSupportedOnThisDevice) {
        Log.d(TAG, "This device is GPU Compatible ")
        Model.Options.Builder().setDevice(Model.Device.GPU).build()
      } else {
        Log.d(TAG, "This device is GPU Incompatible ")
        Model.Options.Builder().setNumThreads(4).build()
      }

      Model1.newInstance(ctx, options)
    }

    override fun analyze(imageProxy: ImageProxy) {
      var tfImage = TensorImage(DataType.FLOAT32)
      tfImage.load(toBitmap(imageProxy))

      val imageProcessor = ImageProcessor.Builder()
        .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
        .build()

      tfImage = imageProcessor.process(tfImage)

      val probabilityProcessor = TensorProcessor.Builder().add(NormalizeOp(0f, 255f)).build()

      val outputs = finalModel.process(probabilityProcessor.process(tfImage.tensorBuffer))

      val outputBuffer = outputs.outputFeature0AsTensorBuffer

      val tensorLabel =
        TensorLabel(arrayListOf("gigi_sehat", "gigi_tidak_sehat"), outputBuffer)

      val probability = tensorLabel.mapWithFloatValue["gigi_sehat"]
      probability?.let {
        if (it > 0.60) {
          binding.tvCondition.text = "gigi_sehat"
          Log.d("MainActivity", "data $it")
          gigi = "gigi sehat"
        } else {
          Log.d("MainActivity", "error $it")
          binding.tvCondition.text = "gigi_tidak_sehat"
          gigi = "gigi tidak sehat"
        }
      }

      if (BuildConfig.DEBUG) {
        Log.d(TAG, "gigi : " + probability)
      }

      imageProxy.close()
    }

    /**
     * Convert Image Proxy to Bitmap
     */
    private val yuvToRgbConverter = YuvToRgbConverter(ctx)
    private lateinit var bitmapBuffer: Bitmap
    private lateinit var rotationMatrix: Matrix

    @SuppressLint("UnsafeExperimentalUsageError", "UnsafeOptInUsageError")
    private fun toBitmap(imageProxy: ImageProxy): Bitmap? {

      val image = imageProxy.image ?: return null

      // Initialise Buffer
      if (!::bitmapBuffer.isInitialized) {
        // The image rotation and RGB image buffer are initialized only once
        Log.d(TAG, "Initalise toBitmap()")
        rotationMatrix = Matrix()
        rotationMatrix.postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
        bitmapBuffer = Bitmap.createBitmap(
          imageProxy.width, imageProxy.height, Bitmap.Config.ARGB_8888
        )
      }

      // Pass image to an image analyser
      yuvToRgbConverter.yuvToRgb(image, bitmapBuffer)

      // Create the Bitmap in the correct orientation
      return Bitmap.createBitmap(
        bitmapBuffer,
        0,
        0,
        bitmapBuffer.width,
        bitmapBuffer.height,
        rotationMatrix,
        false
      )
    }
  }

  private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
    ContextCompat.checkSelfPermission(
      activity?.baseContext!!, it
    ) == PackageManager.PERMISSION_GRANTED
  }

  override fun onDestroy() {
    super.onDestroy()
    cameraExecutor.shutdown()
    Log.d("HomeActivity", gigi)
    preferenceViewModel = obtainPreferenceViewModel(requireActivity())
    historyViewModel = obtainHistoryViewModel(requireActivity())
    historyViewModel.saveHistory(preferenceViewModel.getToken().token.toString(), gigi)
    historyViewModel.getResult().observe(requireActivity()) {}
    historyViewModel.getError().observe(requireActivity()) {}
  }

  private fun obtainHistoryViewModel(requireActivity: FragmentActivity): HistoryViewModel {
    val factory = ViewModelFactory.getInstance(requireActivity)
    return ViewModelProvider(requireActivity, factory).get(HistoryViewModel::class.java)
  }

  private fun obtainPreferenceViewModel(requireActivity: FragmentActivity): PreferenceViewModel {
    val factory = ViewModelFactory.getInstance(requireActivity)
    return ViewModelProvider(requireActivity, factory).get(PreferenceViewModel::class.java)
  }

  companion object {
    private const val TAG = "DaunTeh"
    private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    private const val REQUEST_CODE_PERMISSIONS = 10
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    var gigi: String = ""
  }

}