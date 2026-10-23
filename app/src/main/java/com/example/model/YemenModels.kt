package com.example.model

data class GeoPoint(
    val lat: Double,
    val lng: Double
)

enum class TerrainType(val labelArabic: String) {
    MOUNTAINOUS("جبلية ووديان"),
    COASTAL("ساحلية وبحرية"),
    DESERT("صحراوية وهضاب"),
    ISLAND("أرخبيل وجزر")
}

data class Governorate(
    val id: String,
    val nameArabic: String,
    val nameEnglish: String,
    val capital: String,
    val nickname: String,
    val region: String, // e.g. إقليم آزال، إقليم الجند، إقليم سبأ، إقليم تهامة، إقليم حضرموت، إقليم عدن
    val terrainType: TerrainType,
    val population: String,
    val areaKm2: Int,
    val center: GeoPoint,
    val polygon: List<GeoPoint>,
    val description: String,
    val climate: String,
    val landmarks: List<String>,
    val keyProducts: List<String>,
    val portsAndAirports: List<String>,
    val colorHex: Long
)

enum class LandmarkCategory(val titleArabic: String, val iconEmoji: String) {
    HISTORICAL("قلاع وآثار تاريخية", "🏰"),
    MOUNTAIN("جبال وقمم وتضاريس", "🏔️"),
    WATER_COAST("سواحل ومضائق وبحار", "🌊"),
    NATURE_ISLAND("محميات وجزر طبيعية", "🌴")
}

data class Landmark(
    val id: String,
    val name: String,
    val category: LandmarkCategory,
    val governorateId: String,
    val governorateName: String,
    val location: GeoPoint,
    val description: String,
    val historicalSignificance: String
)

data class CityDistance(
    val fromCity: String,
    val toCity: String,
    val distanceKm: Int,
    val drivingHours: Double,
    val roadCondition: String,
    val routeDescription: String,
    val waypoints: List<String>
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val governorateId: String? = null
)

data class MapFilterState(
    val showBorders: Boolean = true,
    val showGovernorateNames: Boolean = true,
    val showCities: Boolean = true,
    val showLandmarks: Boolean = true,
    val showPorts: Boolean = true,
    val showDistances: Boolean = false
)

enum class AppTab(val title: String, val iconDescription: String) {
    MAP("الخريطة", "الخريطة التفاعلية"),
    NAVIGATION("الملاحة", "ملاحة بدون إنترنت"),
    GOVERNORATES("المحافظات", "دليل المحافظات"),
    DISTANCES("المسافات", "حاسبة المسافات"),
    LANDMARKS("المعالم", "المعالم والتضاريس"),
    QUIZ("اختبر نفسك", "مسابقة جغرافية")
}
