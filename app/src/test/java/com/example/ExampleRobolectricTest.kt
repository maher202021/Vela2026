package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.YemenDataProvider
import com.example.navigation.valhalla.ValhallaOfflineNavigator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("خريطة اليمن", appName)
  }

  @Test
  fun `verify yemen governorates count and search`() {
    val governorates = YemenDataProvider.governorates
    assertEquals(22, governorates.size)

    val aden = YemenDataProvider.getGovernorateById("aden")
    assertNotNull(aden)
    assertEquals("عدن", aden?.nameArabic)

    val sanaa = YemenDataProvider.getGovernorateById("sanaa_capital")
    assertNotNull(sanaa)

    val results = YemenDataProvider.searchGovernorates("حضرموت")
    assertTrue(results.isNotEmpty())
    assertEquals("حضرموت", results.first().nameArabic)
  }

  @Test
  fun `verify landmarks and quiz data`() {
    assertTrue(YemenDataProvider.landmarks.isNotEmpty())
    assertTrue(YemenDataProvider.cityDistances.isNotEmpty())
    assertTrue(YemenDataProvider.quizQuestions.isNotEmpty())
  }

  @Test
  fun `verify valhalla offline navigation engine from bundled tiles zip`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    ValhallaOfflineNavigator.initialize(context)

    val status = ValhallaOfflineNavigator.getStatus()
    assertTrue("Valhalla engine must be ready", status.isReady)
    assertTrue("Valhalla nodes count must be > 20", status.totalNodes > 20)
    assertTrue("Valhalla edges count must be > 30", status.totalEdges > 30)

    // Test routing from Sana'a to Aden
    val routeSanaaAden = ValhallaOfflineNavigator.findRoute("sanaa", "aden")
    assertNotNull("Route Sana'a to Aden must be found", routeSanaaAden)
    assertTrue(routeSanaaAden!!.totalDistanceKm > 300)
    assertTrue(routeSanaaAden.maneuvers.isNotEmpty())
    assertTrue(routeSanaaAden.pathPoints.size >= 4)

    // Test routing from Sana'a to Hodeidah
    val routeSanaaHudaydah = ValhallaOfflineNavigator.findRoute("sanaa", "hudaydah")
    assertNotNull("Route Sana'a to Hodeidah must be found", routeSanaaHudaydah)
    assertTrue(routeSanaaHudaydah!!.totalDistanceKm > 140)
  }
}
