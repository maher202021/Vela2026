package com.example.data

import com.example.model.CityDistance
import com.example.model.GeoPoint
import com.example.model.Governorate
import com.example.model.Landmark
import com.example.model.LandmarkCategory
import com.example.model.QuizQuestion
import com.example.model.TerrainType

object YemenDataProvider {

    val governorates: List<Governorate> = listOf(
        Governorate(
            id = "sanaa_capital",
            nameArabic = "أمانة العاصمة",
            nameEnglish = "Amanat Al Asimah",
            capital = "صنعاء القديمة",
            nickname = "قلب اليمن النابض والتاريخ العريق",
            region = "إقليم آزال",
            terrainType = TerrainType.MOUNTAINOUS,
            population = "حوالي 3,000,000 نسمة",
            areaKm2 = 390,
            center = GeoPoint(15.3547, 44.2075),
            polygon = listOf(
                GeoPoint(15.42, 44.18),
                GeoPoint(15.42, 44.27),
                GeoPoint(15.32, 44.28),
                GeoPoint(15.28, 44.21),
                GeoPoint(15.32, 44.15)
            ),
            description = "العاصمة السياسية والتاريخية للجمهورية اليمنية، تحتضن مدينة صنعاء القديمة المدرجة على قائمة التراث العالمي لليونسكو بنمطها المعماري الفريد ونوافذ القمريات والياجور.",
            climate = "معتدل صيفاً، يميل للبرودة والجفاف شتاءً مع سماء صافية معظم أيام السنة.",
            landmarks = listOf("صنعاء القديمة وباب اليمن", "الجامع الكبير بصنعاء", "متحف التراث الوطني", "سوق الملح التراثي"),
            keyProducts = listOf("صناعة الجنابي التراثية", "العقيق اليماني", "الحلي الفضية", "البن الخولاني"),
            portsAndAirports = listOf("مطار صنعاء الدولي"),
            colorHex = 0xFFD97706
        ),
        Governorate(
            id = "sanaa_gov",
            nameArabic = "محافظة صنعاء",
            nameEnglish = "Sana'a Governorate",
            capital = "صنعاء (الضواحي)",
            nickname = "حزام العاصمة وأرض الحصون والوديان",
            region = "إقليم آزال",
            terrainType = TerrainType.MOUNTAINOUS,
            population = "حوالي 1,300,000 نسمة",
            areaKm2 = 13850,
            center = GeoPoint(15.20, 44.05),
            polygon = listOf(
                GeoPoint(15.70, 43.90),
                GeoPoint(15.65, 44.60),
                GeoPoint(15.00, 44.55),
                GeoPoint(14.75, 44.10),
                GeoPoint(15.00, 43.65),
                GeoPoint(15.40, 43.70)
            ),
            description = "تحيط بالعاصمة من جميع الاتجاهات وتضم أعلى قمة في شبه الجزيرة العربية (جبل النبي شعيب بارتفاع 3666 متراً) ووادي ظهر الشهير بدار الحجر الصخري.",
            climate = "جبلي بارد شتاءً ومعتدل ماطر في فصلي الربيع والصيف.",
            landmarks = listOf("دار الحجر في وادي ظهر", "جبل النبي شعيب (أعلى قمة في الجزيرة العربية)", "حصن كوكبان", "شلال بني مطر"),
            keyProducts = listOf("العنب اليماني الرازقي والأسود", "البن الحرازي والمطري", "الرمان والخوخ", "اللوز البلدي"),
            portsAndAirports = listOf("طرق جبلية رئيسية تربط العاصمة بموانئ البحر الأحمر"),
            colorHex = 0xFFB45309
        ),
        Governorate(
            id = "aden",
            nameArabic = "عدن",
            nameEnglish = "Aden",
            capital = "خور مكسر / كريتر",
            nickname = "ثغر اليمن الباسم والعاصمة الاقتصادية",
            region = "إقليم عدن",
            terrainType = TerrainType.COASTAL,
            population = "حوالي 1,100,000 نسمة",
            areaKm2 = 750,
            center = GeoPoint(12.8000, 45.0300),
            polygon = listOf(
                GeoPoint(12.92, 44.90),
                GeoPoint(12.92, 45.15),
                GeoPoint(12.74, 45.08),
                GeoPoint(12.76, 44.95)
            ),
            description = "شبه جزيرة بركانية طبيعية على خليج عدن وبحر العرب، وميناء استراتيجي عالمي من أقدم الموانئ الطبيعية المحمية في العالم، تشتهر بصهاريجها الأثرية وقلعة صيرة.",
            climate = "ساحلي حار ورطب صيفاً، ومعتدل دافئ ومنعش شتاءً مع نسيم البحر.",
            landmarks = listOf("صهاريج عدن التاريخية", "قلعة صيرة الصخرية", "منارة عدن", "جبل شمسان الشامخ", "ساحل أبين وكورنيش كالتكس"),
            keyProducts = listOf("الثروة السمكية والجمبري", "البخور والفل العدني", "الصناعات الملاحية والتكرير والتجارة الدولية"),
            portsAndAirports = listOf("مطار عدن الدولي", "ميناء عدن الطبيعي الدولي الحُر"),
            colorHex = 0xFF0284C7
        ),
        Governorate(
            id = "taiz",
            nameArabic = "تعز",
            nameEnglish = "Taiz",
            capital = "تعز",
            nickname = "الحالمة وعاصمة الثقافة والعلوم",
            region = "إقليم الجند",
            terrainType = TerrainType.MOUNTAINOUS,
            population = "حوالي 3,200,000 نسمة (الأكثر سكاناً)",
            areaKm2 = 10008,
            center = GeoPoint(13.5795, 44.0209),
            polygon = listOf(
                GeoPoint(13.80, 43.80),
                GeoPoint(13.78, 44.35),
                GeoPoint(13.30, 44.30),
                GeoPoint(13.15, 43.60),
                GeoPoint(13.50, 43.40)
            ),
            description = "عاصمة الدولة الرسولية التاريخية وعاصمة الثقافة اليمنية، تتربع عند سفح جبل صبر الأشم، وتطل على ميناء المخا التاريخي الشهير بتصدير أول شحنات البن (الموكا) للعالم.",
            climate = "معتدل لطيف طوال العام، وماطر صيفاً على قمم جبل صبر والوديان المحيطة.",
            landmarks = listOf("قلعة القاهرة التاريخية", "جامع ومدرسة الأشرفية الرسولية", "جبل صبر الأشم", "ميناء المخا التاريخي", "المظفر"),
            keyProducts = listOf("بن المخا الأصيل (Mocha Coffee)", "الجبن البلدي التعزي", "الفواكه الصيفية والخضروات", "الصناعات الحرفية"),
            portsAndAirports = listOf("ميناء المخا التاريخي", "مطار تعز الدولي"),
            colorHex = 0xFF0D9488
        ),
        Governorate(
            id = "hadhramaut",
            nameArabic = "حضرموت",
            nameEnglish = "Hadhramaut",
            capital = "المكلا",
            nickname = "أرض الحضارة وناطحات السحاب الطينية",
            region = "إقليم حضرموت",
            terrainType = TerrainType.DESERT,
            population = "حوالي 1,600,000 نسمة",
            areaKm2 = 191737, // Largest governorate
            center = GeoPoint(15.90, 48.80),
            polygon = listOf(
                GeoPoint(17.80, 47.50),
                GeoPoint(18.90, 50.00),
                GeoPoint(18.50, 52.00),
                GeoPoint(16.50, 51.50),
                GeoPoint(14.60, 49.30),
                GeoPoint(14.10, 48.40),
                GeoPoint(15.00, 47.20),
                GeoPoint(16.20, 47.00)
            ),
            description = "أكبر محافظات اليمن مساحة تشكل أكثر من ثلث مساحة البلاد. تمتاز بجمال مدن وادي حضرموت مثل شبام (مانهاتن الصحراء) وقصر سيئون الطيني، وشواطئ المكلا الساحرة على بحر العرب.",
            climate = "حار جاف في الصحراء والهضاب صيفاً، معتدل شتاءً، وساحلي رطب في المكلا والشحر.",
            landmarks = listOf("مدينة شبام حضرموت (ناطحات السحاب الطينية)", "قصر سيئون الكثيري التاريخي", "حصن الغويزي بالمكلا", "وادي دوعن والعسل الدوعني", "قبر النبي هود"),
            keyProducts = listOf("العسل الدوعني الملكي الشهير عالمياً", "النفط والغاز", "الأسماك والتونة", "تمور وادي حضرموت واللبان"),
            portsAndAirports = listOf("مطار سيئون الدولي", "مطار الريان الدولي", "ميناء المكلا", "ميناء الشحر"),
            colorHex = 0xFFD97706
        ),
        Governorate(
            id = "ibb",
            nameArabic = "إب",
            nameEnglish = "Ibb",
            capital = "إب",
            nickname = "اللواء الأخضر وعاصمة السياحة الطبيعية",
            region = "إقليم الجند",
            terrainType = TerrainType.MOUNTAINOUS,
            population = "حوالي 2,900,000 نسمة",
            areaKm2 = 5552,
            center = GeoPoint(13.97, 44.18),
            polygon = listOf(
                GeoPoint(14.25, 44.05),
                GeoPoint(14.20, 44.40),
                GeoPoint(13.75, 44.38),
                GeoPoint(13.78, 43.90),
                GeoPoint(14.10, 43.95)
            ),
            description = "تلقب بـ 'اللواء الأخضر' لما حباها الله من بساط أخضر بديع ومدرجات زراعية وشلالات دائمة، تضم مدينة جبلة التاريخية عاصمة الملكة أروى بنت أحمد الصليحي ودار العز.",
            climate = "غزير الأمطار صيفاً، معتدل منعش ضبابي على القمم طيلة موسم الخريف والربيع.",
            landmarks = listOf("مدينة جبلة وجامع السيدة أروى", "وادي بنا الخلاب", "شلالات المشنة ووادي عنّه", "حصن حب التاريخي في بعدان", "جبل ربي"),
            keyProducts = listOf("الحبوب والذرة والشعير", "البن والفاكهة المتنوعة", "العسل الجبلي", "الخضروات والمحاصيل المروية بالأمطار"),
            portsAndAirports = listOf("شبكة طرق شريانية تربط وسط اليمن بجنوبه وغربه"),
            colorHex = 0xFF16A34A
        ),
        Governorate(
            id = "al_hudaydah",
            nameArabic = "الحديدة",
            nameEnglish = "Al Hudaydah",
            capital = "الحديدة",
            nickname = "عروس البحر الأحمر وبوابة تهامة",
            region = "إقليم تهامة",
            terrainType = TerrainType.COASTAL,
            population = "حوالي 2,800,000 نسمة",
            areaKm2 = 13500,
            center = GeoPoint(14.80, 43.10),
            polygon = listOf(
                GeoPoint(15.80, 42.70),
                GeoPoint(15.70, 43.40),
                GeoPoint(14.00, 43.40),
                GeoPoint(13.70, 43.15),
                GeoPoint(14.10, 42.80),
                GeoPoint(15.20, 42.80)
            ),
            description = "تمتد على الشريط الساحلي لسهل تهامة الغربي بمحاذاة البحر الأحمر. تعتبر شريان الإمداد الغذائي والتجاري الرئيسي لليمن عبر مينائها وتشتهر بمدينة زبيد التاريخية الشهيرة عالمياً بالعلم.",
            climate = "حار رطب صيفاً، دافئ ولطيف شتاءً مع نسمات البحر الأحمر.",
            landmarks = listOf("مدينة زبيد التاريخية (تراث عالمي)", "قلعة الكورنيش بالحديدة", "سوق الحوك الشعبي", "جزيرة كمران الطبيعية", "حارة الهنود والباب الشامي"),
            keyProducts = listOf("المانجو التهامي الشهير", "الأسماك والمأكولات البحرية", "التمور والقطن وقصب السكر", "الملح الصخري"),
            portsAndAirports = listOf("ميناء الحديدة التجاري الرئيسي", "ميناء الصليف للحبوب والملح", "مطار الحديدة الدولي"),
            colorHex = 0xFF0284C7
        ),
        Governorate(
            id = "marib",
            nameArabic = "مأرب",
            nameEnglish = "Marib",
            capital = "مأرب",
            nickname = "عاصمة مملكة سبأ وأرض السد العظيم",
            region = "إقليم سبأ",
            terrainType = TerrainType.DESERT,
            population = "حوالي 1,000,000 نسمة",
            areaKm2 = 17405,
            center = GeoPoint(15.45, 45.32),
            polygon = listOf(
                GeoPoint(16.00, 44.80),
                GeoPoint(16.10, 45.90),
                GeoPoint(15.00, 46.10),
                GeoPoint(14.70, 45.20),
                GeoPoint(15.20, 44.70)
            ),
            description = "مهد الحضارة السبئية القديمة المذكورة في القرآن الكريم، موطن سد مأرب العظيم وعرش بلقيس (معبد برّان) ومعبد أوام، ومركز رئيسي لإنتاج الغاز والنفط والطاقة.",
            climate = "صحراوي حار صيفاً، معتدل وجاف شتاءً مع انخفاض درجات الحرارة ليلاً.",
            landmarks = listOf("سد مأرب القديم والجديد", "عرش بلقيس (معبد برّان)", "معبد أوام (محرم بلقيس)", "مدينة براقش الأثرية", "مأرب القديمة"),
            keyProducts = listOf("الغاز الطبيعي المسال والنفط", "الحمضيات والبرتقال المأربي الفاخر", "القمح والحبوب", "العسل السدر الصافي"),
            portsAndAirports = listOf("محطة مأرب الغازية", "مطار صافر"),
            colorHex = 0xFFCA8A04
        ),
        Governorate(
            id = "socotra",
            nameArabic = "سقطرى",
            nameEnglish = "Socotra Archipelago",
            capital = "حديبو",
            nickname = "جزيرة النعيم وجوهرة المحيط الهندي",
            region = "إقليم حضرموت",
            terrainType = TerrainType.ISLAND,
            population = "حوالي 70,000 نسمة",
            areaKm2 = 3796,
            center = GeoPoint(12.46, 53.82),
            polygon = listOf(
                GeoPoint(12.70, 53.40),
                GeoPoint(12.72, 54.30),
                GeoPoint(12.25, 54.35),
                GeoPoint(12.20, 53.45)
            ),
            description = "أرخبيل يمني أسطوري في المحيط الهندي، صُنف موقع تراث طبيعي عالمي لليونسكو. يحتوي على نباتات وحيوانات نادرة لا توجد في أي مكان آخر على كوكب الأرض، أبرزها شجرة دم الأخوين.",
            climate = "استوائي دافئ على مدار السنة، يتأثر بموسم الرياح الموسمية الصيفية من يونيو لأغسطس.",
            landmarks = listOf("أشجار دم الأخوين (Dragon Blood Tree)", "شاطئ قلنسية وبحيرة ديتواه الساحرة", "كهف حوق العملاق", "هضبة دكسم الجبلية", "محمية شوعب البحرية"),
            keyProducts = listOf("صمغ شجرة دم الأخوين الفريد", "العسل السقطري النادر", "اللبان السقطري والبخور", "الأسماك النادرة واللؤلؤ"),
            portsAndAirports = listOf("مطار سقطرى الدولي (حديبو)", "ميناء حولاف بسقطرى"),
            colorHex = 0xFF059669
        ),
        Governorate(
            id = "saada",
            nameArabic = "صعدة",
            nameEnglish = "Sa'ada",
            capital = "صعدة",
            nickname = "بوابة الشمال وحصن الطبيعة الشاهقة",
            region = "إقليم آزال",
            terrainType = TerrainType.MOUNTAINOUS,
            population = "حوالي 1,100,000 نسمة",
            areaKm2 = 12371,
            center = GeoPoint(16.94, 43.76),
            polygon = listOf(
                GeoPoint(17.40, 43.20),
                GeoPoint(17.50, 44.30),
                GeoPoint(16.60, 44.40),
                GeoPoint(16.50, 43.30)
            ),
            description = "تقع في أقصى الشمال الغربي وتحد المملكة العربية السعودية. تمتاز بتراثها الطيني العريق في صعدة القديمة بمنازلها المحصنة وسورها التاريخي، وبإنتاجها الوفير من أجود فواكه اليمن.",
            climate = "معتدل صيفاً، بارد جداً شتاءً حيث تقترب درجات الحرارة من الصفر المئوي.",
            landmarks = listOf("مدينة صعدة التاريخية المسورة", "جامع الإمام الهادي الأثري", "حصن القشلة", "وادي مذاب الزراعي"),
            keyProducts = listOf("الرمان الصعدي الشهير عالمياً", "العنب والتفاح والبرتقال", "البن الخولاني", "الحبوب"),
            portsAndAirports = listOf("منافذ برية دولية شمالية"),
            colorHex = 0xFF7C3AED
        ),
        Governorate(
            id = "shabwah",
            nameArabic = "شبوة",
            nameEnglish = "Shabwah",
            capital = "عتق",
            nickname = "أرض الممالك القديمة وحضارة اللبان",
            region = "إقليم حضرموت",
            terrainType = TerrainType.DESERT,
            population = "حوالي 700,000 نسمة",
            areaKm2 = 42584,
            center = GeoPoint(14.80, 46.80),
            polygon = listOf(
                GeoPoint(15.80, 46.20),
                GeoPoint(15.70, 47.60),
                GeoPoint(14.10, 48.00),
                GeoPoint(13.90, 47.00),
                GeoPoint(14.50, 45.80)
            ),
            description = "موطن عاصمة مملكة حضرموت التاريخية (شبوة القديمة) التي كانت المحطة المركزية لطريق اللبان والبخور التجاري. تمتلك شريطاً ساحلياً غنياً على بحر العرب وميناء بلحاف لتصدير الغاز المسال.",
            climate = "صحراوي حار صيفاً معتدل شتاءً في الداخل، وساحلي دافئ على شواطئ بئر علي.",
            landmarks = listOf("مدينة شبوة التاريخية الأثرية", "ميناء قنا التاريخي (بئر علي)", "حصن عتق القديم", "وادي بيحان التاريخي", "منتجع وشاطئ بئر علي البركاني"),
            keyProducts = listOf("الغاز الطبيعي المسال في بلحاف", "النفط الخام", "عسل السدر الشبواني النقي الجرداني", "الأسماك"),
            portsAndAirports = listOf("ميناء بلحاف لتصدير الغاز المسال", "ميناء النشيمة النفطي", "مطار عتق"),
            colorHex = 0xFFEA580C
        ),
        Governorate(
            id = "al_mahrah",
            nameArabic = "المهرة",
            nameEnglish = "Al Mahrah",
            capital = "الغيضة",
            nickname = "بوابة الشرق وأرض اللبان واللغة المهرية",
            region = "إقليم حضرموت",
            terrainType = TerrainType.COASTAL,
            population = "حوالي 200,000 نسمة",
            areaKm2 = 67297,
            center = GeoPoint(16.50, 51.50),
            polygon = listOf(
                GeoPoint(18.50, 51.00),
                GeoPoint(18.00, 52.90),
                GeoPoint(16.50, 53.00),
                GeoPoint(15.50, 51.80),
                GeoPoint(15.80, 50.80),
                GeoPoint(17.00, 50.50)
            ),
            description = "تقع في أقصى شرق اليمن على الحدود مع سلطنة عُمان، تمتاز بخصوصيتها اللغوية والثقافية (اللغة المهرية القديمة) وبطبيعتها الخلابة في محمية حوف التي تكتسي بالضباب والخضرة في فصل الخريف.",
            climate = "معتدل خريفي رائع في جبال حوف بفعل الرياح الموسمية، وصحراوي جاف شمالاً.",
            landmarks = listOf("محمية حوف الطبيعية الساحرة", "ميناء نشطون", "شواطئ الغيضة الذهبية", "قصر السلطان عفرار في قشن"),
            keyProducts = listOf("شجرة اللبان المهري الأصيل", "الثروة السمكية الهائلة (الشارخة والحبار)", "الإبل المهرية الأصيلة", "العسل"),
            portsAndAirports = listOf("مطار الغيضة الدولي", "ميناء نشطون التجاري والسمكي"),
            colorHex = 0xFF059669
        ),
        Governorate(
            id = "lahij",
            nameArabic = "لحج",
            nameEnglish = "Lahij",
            capital = "الحوطة",
            nickname = "لحج الخضيرة وأرض الفن والقمندان",
            region = "إقليم عدن",
            terrainType = TerrainType.COASTAL,
            population = "حوالي 1,100,000 نسمة",
            areaKm2 = 13046,
            center = GeoPoint(13.15, 44.60),
            polygon = listOf(
                GeoPoint(13.50, 44.20),
                GeoPoint(13.60, 45.00),
                GeoPoint(12.70, 44.80),
                GeoPoint(12.60, 43.50),
                GeoPoint(13.10, 43.70)
            ),
            description = "تقع شمال وغرب عدن وتتحكم بالممر المائي الأهم في العالم (مضيق باب المندب وجزيرة ميون). تشتهر ببساتين الحسيني والشعر والغناء اللحجي الأصيل للموسيقار القمندان.",
            climate = "حار صيفاً معتدل شتاءً في السهول، ومعتدل طوال العام في جبال يافع الشاهقة.",
            landmarks = listOf("مضيق باب المندب الاستراتيجي", "بستان الحسيني الشهير", "جبال يافع الشامخة وقصورها الحجرية المشيدة", "قصر الروضة بالحوطة"),
            keyProducts = listOf("الفل اللحجي العبق", "البن اليافعي الفاخر", "المانجو والباباي والخضار", "الأسماك في رأس العارة"),
            portsAndAirports = listOf("ممر مضيق باب المندب الدولي", "مرسى رأس العارة"),
            colorHex = 0xFF16A34A
        ),
        Governorate(
            id = "abyan",
            nameArabic = "أبين",
            nameEnglish = "Abyan",
            capital = "زنجبار",
            nickname = "سلة الغذاء الجنوبي وأرض السواحل الممتدة",
            region = "إقليم عدن",
            terrainType = TerrainType.COASTAL,
            population = "حوالي 650,000 نسمة",
            areaKm2 = 16943,
            center = GeoPoint(13.50, 45.80),
            polygon = listOf(
                GeoPoint(14.00, 45.20),
                GeoPoint(14.20, 46.20),
                GeoPoint(13.40, 46.70),
                GeoPoint(13.00, 45.50),
                GeoPoint(13.40, 45.00)
            ),
            description = "تقع على ساحل بحر العرب شرق عدن وتشتهر بدلتا أبين الخصبة المروية بوادي بنا ووادي حسان، وتعتبر من أهم مصادر المنتجات الزراعية والثروة السمكية والحيوانية.",
            climate = "حار رطب ساحلياً صيفاً ومعتدل دافئ شتاءً، ومعتدل في مرتفعات الكور.",
            landmarks = listOf("دلتا أبين الزراعية", "سد باتيس ووادي بنا", "حصن الفضلي", "شواطئ شقرة والخبر الساحرة على بحر العرب"),
            keyProducts = listOf("القطن طويل التيلة", "الموز والمانجو والبطيخ", "السمسم وزيت الجلجلان", "الأسماك المتنوعة"),
            portsAndAirports = listOf("ميناء شقرة السمكي التراثي"),
            colorHex = 0xFF0284C7
        ),
        Governorate(
            id = "dhamar",
            nameArabic = "ذمار",
            nameEnglish = "Dhamar",
            capital = "ذمار",
            nickname = "كرسي الزيدية ومهد الخيول العربية الأصيلة",
            region = "إقليم آزال",
            terrainType = TerrainType.MOUNTAINOUS,
            population = "حوالي 2,100,000 نسمة",
            areaKm2 = 7973,
            center = GeoPoint(14.55, 44.38),
            polygon = listOf(
                GeoPoint(14.90, 44.00),
                GeoPoint(14.95, 44.80),
                GeoPoint(14.25, 44.80),
                GeoPoint(14.20, 43.90)
            ),
            description = "هضبة جبلية بركانية خصبة في قلب اليمن، اشتهرت منذ القدم بتربية الخيول العربية الأصيلة (مربط الخيول) وتضم مدينة ذمار التاريخية وجامعتها ومتحف بينون الأثري ومحمية جبل اللسي البركاني.",
            climate = "بارد شتاءً مع صقيع على الهضاب، معتدل ماطر صيفاً.",
            landmarks = listOf("متحف بينون التاريخي", "جبل اللسي البركاني ومحطة الطاقة الحرارية", "حمام دمت الكبريتي الاستشفائي (المجاور)", "مدينة ذمار القديمة والجامع الكبير"),
            keyProducts = listOf("الخيول العربية الأصيلة", "البطاطس والقمح والشعير", "البن الحنسي", "الرخام والجرانيت وحجر البناء"),
            portsAndAirports = listOf("طريق صنعاء - تعز - عدن الرئيسي"),
            colorHex = 0xFF9333EA
        ),
        Governorate(
            id = "al_bayda",
            nameArabic = "البيضاء",
            nameEnglish = "Al Bayda",
            capital = "البيضاء",
            nickname = "قلب الهضبة الوسطى وملتقى المحافظات",
            region = "إقليم سبأ",
            terrainType = TerrainType.MOUNTAINOUS,
            population = "حوالي 800,000 نسمة",
            areaKm2 = 9314,
            center = GeoPoint(14.20, 45.45),
            polygon = listOf(
                GeoPoint(14.65, 45.00),
                GeoPoint(14.70, 45.90),
                GeoPoint(13.80, 45.80),
                GeoPoint(13.85, 44.95)
            ),
            description = "تحتل موقعاً جغرافياً استراتيجياً يتوسط محافظات اليمن شمالاً وجنوباً وشرقاً وغرباً، تشتهر بمدينة رداع التاريخية ومدرستها العامرية الإسلامية الشاهدة على العمارة الطاهرية البديعة.",
            climate = "معتدل صيفاً وبارد شتاءً في المرتفعات، ودافئ في المناطق المنخفضة.",
            landmarks = listOf("المدرسة العامرية التاريخية برداع", "قلعة رداع الأثرية", "حصن البيضاء القديم", "وادي بيحان الجنوبي"),
            keyProducts = listOf("الحبوب والذرة الشامية", "العسل الدوعني والبلدي", "العنب والرمان والخوخ", "الجلود والصوف"),
            portsAndAirports = listOf("شبكة طرق محورية تربط مأرب وشبوة وأبين وذمار"),
            colorHex = 0xFFD97706
        ),
        Governorate(
            id = "hajjah",
            nameArabic = "حجة",
            nameEnglish = "Hajjah",
            capital = "حجة",
            nickname = "مدينة الضباب وأرض القلاع المعلقة",
            region = "إقليم تهامة",
            terrainType = TerrainType.MOUNTAINOUS,
            population = "حوالي 2,300,000 نسمة",
            areaKm2 = 8227,
            center = GeoPoint(15.70, 43.50),
            polygon = listOf(
                GeoPoint(16.50, 42.80),
                GeoPoint(16.40, 43.70),
                GeoPoint(15.40, 43.60),
                GeoPoint(15.50, 42.80)
            ),
            description = "تجمع بين الجبال الشاهقة المكسوة بالضباب وسهول تهامة الساحلية الممتدة حتى البحر الأحمر عبر ميدي وحرض، وتشتهر بقلعة القاهرة بحجة وحصن ظفير التاريخي ومناظرها المهيبة.",
            climate = "معتدل بارد ممطر على القمم الجبلية صيفاً، وحار رطب في السهل الساحلي بميدي.",
            landmarks = listOf("قلعة القاهرة في حجة", "مدينة ظفير الجبلية المحصنة", "وادي مور الزراعي العملاق", "ميناء ميدي على البحر الأحمر"),
            keyProducts = listOf("البن الخولاني الشامي", "السمسم والذرة", "الحبوب والعسل التهامي", "الأسماك في ساحل ميدي"),
            portsAndAirports = listOf("ميناء ميدي على البحر الأحمر", "منفذ حرض الحدودي"),
            colorHex = 0xFF4F46E5
        ),
        Governorate(
            id = "amran",
            nameArabic = "عمران",
            nameEnglish = "Amran",
            capital = "عمران",
            nickname = "حاضرة حمير وبوابة الجوف وصعدة",
            region = "إقليم آزال",
            terrainType = TerrainType.MOUNTAINOUS,
            population = "حوالي 1,200,000 نسمة",
            areaKm2 = 7901,
            center = GeoPoint(16.00, 43.95),
            polygon = listOf(
                GeoPoint(16.45, 43.60),
                GeoPoint(16.50, 44.30),
                GeoPoint(15.60, 44.20),
                GeoPoint(15.65, 43.60)
            ),
            description = "محافظة ذات عمق أثري يعود لمملكة حمير وحمير الصغرى، تضم مدناً تاريخية مسورة فريدة كمدينة ثلاء التاريخية المكتملة ومدينة حبابة بصهريجها الحجري البديع وشهارة بجسرها المعلق الشهير.",
            climate = "معتدل صيفاً يميل للبرودة الشديدة شتاءً.",
            landmarks = listOf("جسر شهارة المعلق التاريخي المشهور عالمياً", "مدينة ثلاء التاريخية المسورة", "بركة وصهريج حبابة الحجري", "مدينة ذيبين التاريخية"),
            keyProducts = listOf("أحجار البناء والرخام العمراني الشهير", "القمح والشعير والذرة", "العنب والتين واللوز", "البن"),
            portsAndAirports = listOf("الطريق الدولي الشمالي"),
            colorHex = 0xFF2563EB
        ),
        Governorate(
            id = "al_jawf",
            nameArabic = "الجوف",
            nameEnglish = "Al Jawf",
            capital = "الحزم",
            nickname = "مهد مملكة معين وأرض الآثار المطمورة",
            region = "إقليم سبأ",
            terrainType = TerrainType.DESERT,
            population = "حوالي 650,000 نسمة",
            areaKm2 = 39496,
            center = GeoPoint(16.60, 45.20),
            polygon = listOf(
                GeoPoint(17.50, 44.30),
                GeoPoint(17.40, 46.50),
                GeoPoint(16.00, 46.20),
                GeoPoint(16.00, 44.50)
            ),
            description = "سهل صحراوي ورملي واسع في شمال شرق اليمن، كان القلب النابض لمملكة مَعين اليمنية القديمة، يحتضن عشرات المدن والمدونات التاريخية مثل مدينة قرناو البيضاء ويثيل (براقش).",
            climate = "صحراوي جاف، حار نهاراً وبارد ليلاً، وتزدهر فيه الزراعة المعتمدة على المياه الجوفية.",
            landmarks = listOf("مدينة براقش (يثيل المعينية بسورها الشامخ)", "قرناو عاصمة مملكة معين القديمة", "مدينة نشن الأثرية (السوداء)", "واحات وادي الخرد"),
            keyProducts = listOf("القمح والذرة الشامية", "التمور والبرتقال والرمان", "الثروة الحيوانية والإبل الأصيلة", "المياه الجوفية العذبة"),
            portsAndAirports = listOf("طرق برية ممتدة نحو نجران وصحراء الربع الخالي"),
            colorHex = 0xFFCA8A04
        ),
        Governorate(
            id = "al_dhalea",
            nameArabic = "الضالع",
            nameEnglish = "Al Dhalea",
            capital = "الضالع",
            nickname = "بوابة الجنوب وحصون الشموخ",
            region = "إقليم عدن",
            terrainType = TerrainType.MOUNTAINOUS,
            population = "حوالي 650,000 نسمة",
            areaKm2 = 4786,
            center = GeoPoint(13.70, 44.73),
            polygon = listOf(
                GeoPoint(14.00, 44.50),
                GeoPoint(14.05, 45.00),
                GeoPoint(13.50, 44.95),
                GeoPoint(13.45, 44.50)
            ),
            description = "محافظة جبلية ترتفع في منتصف المسافة بين العاصمة صنعاء وعدن، تشتهر بجبالها الحصينة مثل جبل جحاف وتاريخها النضالي وعمارتها الجبلية المتقنة وحماماتها الكبريتية في دمت.",
            climate = "معتدل صيفاً يميل للبرودة شتاءً على قمم الجبال.",
            landmarks = listOf("حمام دمت الكبريتي الطبيعي وحفرة دمت البركانية", "قلعة الضالع التاريخية (دار الحيد)", "جبل جحاف الشامخ", "حصن يخلة"),
            keyProducts = listOf("البن الخولاني واليافعي", "الرمان والمشمش والخوخ", "السياحة العلاجية والاستشفائية في دمت", "الحبوب"),
            portsAndAirports = listOf("الخط الرئيسي الحيوي صنعاء - دمت - قعطبة - الضالع - عدن"),
            colorHex = 0xFF0891B2
        ),
        Governorate(
            id = "al_mahwit",
            nameArabic = "المحويت",
            nameEnglish = "Al Mahwit",
            capital = "المحويت",
            nickname = "لؤلؤة الجبال وأرض المدرجات السحابية",
            region = "إقليم آزال",
            terrainType = TerrainType.MOUNTAINOUS,
            population = "حوالي 750,000 نسمة",
            areaKm2 = 2328,
            center = GeoPoint(15.47, 43.55),
            polygon = listOf(
                GeoPoint(15.65, 43.35),
                GeoPoint(15.65, 43.80),
                GeoPoint(15.20, 43.75),
                GeoPoint(15.20, 43.35)
            ),
            description = "واحدة من أجمل محافظات اليمن طبيعة ومدرجات خضراء معلقة بين السحاب والوديان السحيقة كوادي سردد، تتميز بهوائها العليل ومنازلها المعلقة على الحواف الصخرية الشاهقة.",
            climate = "معتدل عليل صيفاً مفعم بالضباب والأمطار، ولطيف شتاءً.",
            landmarks = listOf("مدينة المحويت القديمة وحصنها", "وادي سردد الخصيب", "مدينة كوكبان التاريخية القريبة", "شلالات بني حبش وجبل حفاش"),
            keyProducts = listOf("البن اليمني ذو النكهة المميزة", "التبغ والسمسم", "الفواكه الاستوائية في الوديان (الموز والمانجو)", "العسل الجبلي"),
            portsAndAirports = listOf("طريق المحويت - الحديدة وطريق المحويت - صنعاء"),
            colorHex = 0xFF15803D
        ),
        Governorate(
            id = "raymah",
            nameArabic = "ريمة",
            nameEnglish = "Raymah",
            capital = "الجبين",
            nickname = "حديقة اليمن المعلقة وسيدة الضباب",
            region = "إقليم تهامة",
            terrainType = TerrainType.MOUNTAINOUS,
            population = "حوالي 600,000 نسمة",
            areaKm2 = 1915,
            center = GeoPoint(14.63, 43.71),
            polygon = listOf(
                GeoPoint(14.85, 43.55),
                GeoPoint(14.85, 43.90),
                GeoPoint(14.40, 43.85),
                GeoPoint(14.40, 43.55)
            ),
            description = "أصغر محافظات اليمن مساحة وأكثفها خضرة ومدرجات طبيعية مدهشة تتسلق الجبال حتى القمم الشاهقة. تلقب بحديقة اليمن المعلقة لخصوبتها الفائقة ومناظرها التي تعانق الغيوم.",
            climate = "ماطر بغزارة صيفاً وربيعاً، معتدل لطيف تغطيه سحب الضباب صيفاً وبارد منعش شتاءً.",
            landmarks = listOf("جبل برد (أعلى قمة في ريمة)", "حصن مسور التاريخي", "مدينة الجبين المعلقة", "غابات ومدرجات كسمة ومزهر"),
            keyProducts = listOf("البن الريمي الفاخر عالي الجودة", "العسل الجبلي الأبيض", "الذرة والحبوب", "الفواكه الجبلية المتنوعة"),
            portsAndAirports = listOf("طرق ومسارات جبلية متصلة بالسهل التهامي والحديدة"),
            colorHex = 0xFF16A34A
        )
    )

    val landmarks: List<Landmark> = listOf(
        Landmark(
            id = "dar_al_hajar",
            name = "دار الحجر (قصر الصخرة)",
            category = LandmarkCategory.HISTORICAL,
            governorateId = "sanaa_gov",
            governorateName = "محافظة صنعاء",
            location = GeoPoint(15.4387, 44.1287),
            description = "تحفة معمارية يمنية فريدة مشيدة على صخرة غرامية عملاقة في وادي ظهر الخصيب، بناها الإمام يحيى حميد الدين في مطلع القرن العشرين على أنقاض قصر سبئي قديم.",
            historicalSignificance = "رمز بصري وطني لليمن يجسد الإعجاز المعماري في تطويع التضاريس الصخرية."
        ),
        Landmark(
            id = "old_sanaa",
            name = "مدينة صنعاء القديمة وباب اليمن",
            category = LandmarkCategory.HISTORICAL,
            governorateId = "sanaa_capital",
            governorateName = "أمانة العاصمة",
            location = GeoPoint(15.3547, 44.2155),
            description = "مدينة حية مأهولة منذ أكثر من 2500 عام بتصاميم الياجور والقمريات والزخارف الجصية البيضاء، محاطة بسور تاريخي وبوابتها الرئيسية الأشهر 'باب اليمن'.",
            historicalSignificance = "موقع تراث عالمي لليونسكو، من أقدم المدن المأهولة المتواصلة في التاريخ الإنساني."
        ),
        Landmark(
            id = "shibam_hadhramaut",
            name = "مدينة شبام حضرموت (مانهاتن الصحراء)",
            category = LandmarkCategory.HISTORICAL,
            governorateId = "hadhramaut",
            governorateName = "حضرموت",
            location = GeoPoint(15.9269, 48.6272),
            description = "أقدم مدينة ناطحات سحاب طينية في العالم ترتفع مبانيها حتى 7 و 8 طوابق مبنية بالكامل من الطوب اللبن في وادي حضرموت منذ أكثر من 500 عام.",
            historicalSignificance = "تراث عالمي لليونسكو، نموذج مذهل في التخطيط الحضري والهندسة المعمارية الطينية المقاومة للزمن."
        ),
        Landmark(
            id = "dragon_blood_socotra",
            name = "شجرة دم الأخوين - سقطرى",
            category = LandmarkCategory.NATURE_ISLAND,
            governorateId = "socotra",
            governorateName = "سقطرى",
            location = GeoPoint(12.5028, 53.9500),
            description = "شجرة أسطورية تأخذ شكل المظلة الكثيفة تنمو على هضاب جزيرة سقطرى وتفرز صمغاً راتنجياً أحمر قاني استُخدم قديماً في الطب والتحنيط والأصباغ الملكية.",
            historicalSignificance = "رمز فريد للتنوع الحيوي العالمي المحمي في أرخبيل سقطرى (موقع تراث طبيعي عالمي)."
        ),
        Landmark(
            id = "marib_dam",
            name = "سد مأرب التاريخي وعرش بلقيس",
            category = LandmarkCategory.HISTORICAL,
            governorateId = "marib",
            governorateName = "مأرب",
            location = GeoPoint(15.3986, 45.2444),
            description = "سد مأرب العظيم أعظم منشأة هندسية مائية في العالم القديم ذكرت في القرآن الكريم، وبجواره عرش بلقيس (معبد برّان) بأعمدته الحجرية المنحوتة المدهشة.",
            historicalSignificance = "شاهد على عظمة مملكة سبأ وحضارتها الهندسية والزراعية التي حولت الصحراء إلى جنان."
        ),
        Landmark(
            id = "bab_al_mandeb",
            name = "مضيق باب المندب وجزيرة ميون",
            category = LandmarkCategory.WATER_COAST,
            governorateId = "lahij",
            governorateName = "لحج",
            location = GeoPoint(12.5833, 43.3333),
            description = "المضيق المائي الاستراتيجي الذي يصل البحر الأحمر بخليج عدن والمحيط الهندي، وتتوسطه جزيرة ميون البركانية اليمنية محددة مسار التجارة البحرية العالمية.",
            historicalSignificance = "من أهم وأكثر المضائق والممرات البحرية حيوية للتجارة الدولية ونقل الطاقة في العالم."
        ),
        Landmark(
            id = "cairo_castle_taiz",
            name = "قلعة القاهرة وجبل صبر",
            category = LandmarkCategory.HISTORICAL,
            governorateId = "taiz",
            governorateName = "تعز",
            location = GeoPoint(13.5681, 44.0150),
            description = "حصن عسكري منيع ومدينة ملكية بنيت على مرتفع صخري شاهق يطل على مدينة تعز شيدها الصليحيون وطورها ملوك بني رسول في القرن الثاني عشر.",
            historicalSignificance = "مقر الحكم الرسولي الذهبي وحصن الدفاع الأبرز لمدينة تعز عبر القرون."
        ),
        Landmark(
            id = "aden_cisterns",
            name = "صهاريج عدن وقلعة صيرة",
            category = LandmarkCategory.HISTORICAL,
            governorateId = "aden",
            governorateName = "عدن",
            location = GeoPoint(12.7758, 45.0289),
            description = "صهاريج مائية حجرية عملاقة منحوتة في وادي الطويلة تحت جبل شمسان لجمع مياه الأمطار تعود لعصر حمير، وبجوارها قلعة صيرة الصخرية التي تحرس الميناء.",
            historicalSignificance = "إعجاز هيدروليكي يمني قديم في تخزين المياه العذبة وميناء بحري طبيعي أسطوري."
        ),
        Landmark(
            id = "jabal_nabi_shuayb",
            name = "جبل النبي شعيب (قمة الجزيرة العربية)",
            category = LandmarkCategory.MOUNTAIN,
            governorateId = "sanaa_gov",
            governorateName = "محافظة صنعاء",
            location = GeoPoint(15.2800, 43.9700),
            description = "أعلى قمة جبلية في شبه الجزيرة العربية وفي بلاد الشام بارتفاع 3666 متراً فوق مستوى سطح البحر، تكسوه الغيوم وتطل قمته على مشاهد بانورامية آسرة.",
            historicalSignificance = "أعلى قمة في الجزيرة العربية ومعلم جغرافي فارق في مناخ وهيدرولوجيا اليمن."
        ),
        Landmark(
            id = "shehara_bridge",
            name = "جسر شهارة المعلق التاريخي",
            category = LandmarkCategory.HISTORICAL,
            governorateId = "amran",
            governorateName = "عمران",
            location = GeoPoint(16.1833, 43.7000),
            description = "جسر قوسي حجري أسطوري معلق شيد في القرن السابع عشر ليربط بين قمتي جبلين شاهقين فوق هوة سحيقة بعمق مئات الأمتار ليصد الغزاة.",
            historicalSignificance = "معجزة الهندسة الحجرية الجبلية اليمنية ورمز للصمود والمنعة."
        ),
        Landmark(
            id = "seiyun_palace",
            name = "قصر سيئون الكثيري",
            category = LandmarkCategory.HISTORICAL,
            governorateId = "hadhramaut",
            governorateName = "حضرموت",
            location = GeoPoint(15.9392, 48.7903),
            description = "أحد أكبر وأجمل القصور والمباني الطينية المبنية من الطوب اللبن في العالم، كان مقراً لسلاطين الدولة الكثيرية في وادي حضرموت.",
            historicalSignificance = "أكبر صرح معماري طيني تقليدي لا يزال قائماً بكامل أبراجه وزخارفه."
        ),
        Landmark(
            id = "zebid_city",
            name = "مدينة زبيد التاريخية وجامعتها",
            category = LandmarkCategory.HISTORICAL,
            governorateId = "al_hudaydah",
            governorateName = "الحديدة",
            location = GeoPoint(14.1958, 43.3167),
            description = "مدينة العلم والعلماء في سهل تهامة، كانت عاصمة اليمن في عهد الدولة الزيادية واشتهرت بجامعها الكبير وبكونها مركزاً رائداً لتأسيس علم الجبر وتخريج العلماء.",
            historicalSignificance = "موقع تراث عالمي لليونسكو ومنارة فكرية وإسلامية عريقة في العالم العربي."
        ),
        Landmark(
            id = "jabal_saber",
            name = "جبل صبر الأشم",
            category = LandmarkCategory.MOUNTAIN,
            governorateId = "taiz",
            governorateName = "تعز",
            location = GeoPoint(13.5186, 44.0489),
            description = "ثاني أعلى جبل في اليمن بارتفاع يصل إلى 3070 متراً، يحتضن عشرات القرى والمزارع المعلقة ويطل بإطلالة خلابة على سهول تعز وميناء المخا.",
            historicalSignificance = "معلم طبيعي وأدبي ألهم الشعراء والمطربين اليمنيين على مر الأجيال."
        ),
        Landmark(
            id = "hawf_reserve",
            name = "محمية حوف الطبيعية",
            category = LandmarkCategory.NATURE_ISLAND,
            governorateId = "al_mahrah",
            governorateName = "المهرة",
            location = GeoPoint(16.6500, 52.8000),
            description = "غابة ساحلية مدارية فريدة في شبه الجزيرة العربية تكتسي بالضباب الدائم والأمطار الموسمية خلال خريف المهرة لتتحول إلى واحة خضراء كثيفة الأشجار.",
            historicalSignificance = "أكبر غابة ومحمية طبيعية للمها والوعول والنباتات النادرة في شرق اليمن."
        ),
        Landmark(
            id = "damt_hot_springs",
            name = "حمامات دمت الاستشفائية والحفرة البركانية",
            category = LandmarkCategory.WATER_COAST,
            governorateId = "al_dhalea",
            governorateName = "الضالع",
            location = GeoPoint(14.1742, 44.7075),
            description = "ينابيع كبريتية حارة تنبع طبيعياً من باطن الأرض بجوار حردة دمت (فوهة بركانية قديمة)، يقصدها الزوار للاستشفاء والاسترخاء.",
            historicalSignificance = "أشهر مركز للسياحة العلاجية والجيولوجية الطبيعية في اليمن."
        )
    )

    val cityDistances: List<CityDistance> = listOf(
        CityDistance(
            fromCity = "صنعاء",
            toCity = "عدن",
            distanceKm = 363,
            drivingHours = 6.5,
            roadCondition = "طريق جبلي رئيسي أسفلتي مع منعطفات وهضاب",
            routeDescription = "عبر ذمار - يريم - إب - تعز - الحوبان - لحج ثم عدن (أو مسار الضالع)",
            waypoints = listOf("ذمار", "يريم", "إب", "تعز", "الحوطة (لحج)")
        ),
        CityDistance(
            fromCity = "صنعاء",
            toCity = "تعز",
            distanceKm = 256,
            drivingHours = 4.5,
            roadCondition = "طريق دولي جبلي عبر قمم نقيل يسلح وسمارة",
            routeDescription = "صنعاء - معبر - ذمار - يريم - نقيل سمارة - إب - القاعدة - تعز",
            waypoints = listOf("معبر", "ذمار", "نقيل سمارة", "إب", "القاعدة")
        ),
        CityDistance(
            fromCity = "صنعاء",
            toCity = "الحديدة",
            distanceKm = 226,
            drivingHours = 4.0,
            roadCondition = "طريق مناخة الجبلي المنحدر نحو سهل تهامة الساحلي",
            routeDescription = "صنعاء - متنة - مناخة (جبال حراز) - باب الناقة - باجل - الحديدة",
            waypoints = listOf("متنة", "مناخة", "القطيع", "باجل")
        ),
        CityDistance(
            fromCity = "عدن",
            toCity = "المكلا (حضرموت)",
            distanceKm = 620,
            drivingHours = 8.5,
            roadCondition = "طريق ساحلي سريع بمحاذاة بحر العرب",
            routeDescription = "عدن - زنجبار - شقرة - أحور - بئر علي (شبوة) - بروم - المكلا",
            waypoints = listOf("زنجبار", "شقرة", "أحور", "بئر علي", "بروم")
        ),
        CityDistance(
            fromCity = "صنعاء",
            toCity = "مأرب",
            distanceKm = 173,
            drivingHours = 3.0,
            roadCondition = "طريق جبلي وصحراوي عبر نهم أو صرواح",
            routeDescription = "صنعاء - نهم - مفرق الجوف - مدينة مأرب وسد مأرب",
            waypoints = listOf("فرضة نهم", "مفرق الجوف", "مأرب الجديدة")
        ),
        CityDistance(
            fromCity = "صنعاء",
            toCity = "صعدة",
            distanceKm = 242,
            drivingHours = 4.0,
            roadCondition = "طريق جبلي هضبي باتجاه الشمال",
            routeDescription = "صنعاء - ريدة - عمران - خمر - حوث - حرف سفيان - صعدة",
            waypoints = listOf("عمران", "خمر", "حوث", "حرف سفيان")
        ),
        CityDistance(
            fromCity = "المكلا",
            toCity = "سيئون (وادي حضرموت)",
            distanceKm = 320,
            drivingHours = 4.5,
            roadCondition = "طريق عقبة عبد الله غريب الجبلية نحو الهضبة والوادي",
            routeDescription = "المكلا - عقبة عبد الله غريب - وادي العين - حورة - القطن - شبام - سيئون",
            waypoints = listOf("عقبة عبد الله غريب", "القطن", "شبام حضرموت")
        ),
        CityDistance(
            fromCity = "المكلا",
            toCity = "الغيضة (المهرة)",
            distanceKm = 510,
            drivingHours = 7.0,
            roadCondition = "طريق ساحلي حديث على طول شواطئ بحر العرب الشرقية",
            routeDescription = "المكلا - الشحر - الحامي - الديس الشرقية - الريدة - قشن - نشطون - الغيضة",
            waypoints = listOf("الشحر", "الديس الشرقية", "قشن", "ميناء نشطون")
        ),
        CityDistance(
            fromCity = "تعز",
            toCity = "الحديدة (عبر الساحل)",
            distanceKm = 260,
            drivingHours = 4.0,
            roadCondition = "طريق الساحل الغربي عبر ميناء المخا وحيس",
            routeDescription = "تعز - المخا - الخوخة - حيس - الجراحي - زبيد - بيت الفقيه - الحديدة",
            waypoints = listOf("المخا", "الخوخة", "حيس", "زبيد", "بيت الفقيه")
        ),
        CityDistance(
            fromCity = "صنعاء",
            toCity = "إب",
            distanceKm = 193,
            drivingHours = 3.5,
            roadCondition = "طريق أسفلتي عبر نقيل سمارة المعشوشب",
            routeDescription = "صنعاء - ذمار - يريم - كتاب - سمارة - الدليل - مدينة إب",
            waypoints = listOf("ذمار", "يريم", "نقيل سمارة", "الدليل")
        )
    )

    val quizQuestions: List<QuizQuestion> = listOf(
        QuizQuestion(
            id = 1,
            question = "ما هي أكبر محافظة يمنية من حيث المساحة الجغرافية؟",
            options = listOf("حضرموت", "المهرة", "شبوة", "مأرب"),
            correctIndex = 0,
            explanation = "محافظة حضرموت هي كبرى محافظات الجمهورية اليمنية مساحة، حيث تشكل وحدها أكثر من ثلث مساحة البلاد الإجمالية (حوالي 191,737 كم²)."
        ),
        QuizQuestion(
            id = 2,
            question = "أين تقع قمة 'جبل النبي شعيب' أعلى قمة جبلية في شبه الجزيرة العربية؟",
            options = listOf("محافظة تعز", "محافظة صنعاء", "محافظة إب", "محافظة حجة"),
            correctIndex = 1,
            explanation = "يقع جبل النبي شعيب في مديرية بني مطر بمحافظة صنعاء، ويبلغ ارتفاع قمته 3666 متراً فوق سطح البحر، وهي أعلى قمة في الجزيرة العربية."
        ),
        QuizQuestion(
            id = 3,
            question = "أي المحافظات اليمنية تشتهر عالمياً بوجود شجرة 'دم الأخوين' الفريدة؟",
            options = listOf("أرخبيل سقطرى", "محافظة المهرة", "محافظة أبين", "محافظة عدن"),
            correctIndex = 0,
            explanation = "شجرة دم الأخوين الأسطورية تستوطن أرخبيل سقطرى في المحيط الهندي، وتعد من أندر وأغرب الأشجار على وجه الأرض."
        ),
        QuizQuestion(
            id = 4,
            question = "ما هي المحافظة التي تُلقب بـ 'اللواء الأخضر' لطبيعتها الخلابة ومدرجاتها؟",
            options = listOf("محافظة إب", "محافظة المحويت", "محافظة ذمار", "محافظة لحج"),
            correctIndex = 0,
            explanation = "محافظة إب هي اللواء الأخضر وعاصمة السياحة الطبيعية لليمن بفضل وفرة أمطارها وخضرة جبالها ووديانها الساحرة طوال العام."
        ),
        QuizQuestion(
            id = 5,
            question = "مدينة 'شبام' المعروفة باسم (مانهاتن الصحراء) تقع في أي محافظة؟",
            options = listOf("حضرموت", "مأرب", "الجوف", "شبوة"),
            correctIndex = 0,
            explanation = "مدينة شبام حضرموت الطينية التاريخية تقع في وادي حضرموت، وتعد أقدم مدينة ناطحات سحاب في التاريخ الإنساني مسجلة في اليونسكو."
        ),
        QuizQuestion(
            id = 6,
            question = "ما هو المضيق المائي الاستراتيجي الذي تشرف عليه اليمن ويصل البحر الأحمر بخليج عدن؟",
            options = listOf("مضيق باب المندب", "مضيق هرمز", "مضيق ملقا", "قناة السويس"),
            correctIndex = 0,
            explanation = "مضيق باب المندب يقع تحت السيادة اليمنية عند محافظة لحج ويتحكم بأحد أهم طرق الملاحة والتجارة الدولية بين الشرق والغرب."
        ),
        QuizQuestion(
            id = 7,
            question = "ما هي عاصمة الدولة الرسولية التاريخية وعاصمة الثقافة اليمنية الحالية؟",
            options = listOf("مدينة تعز", "مدينة صنعاء", "مدينة زبيد", "مدينة جبلة"),
            correctIndex = 0,
            explanation = "تعز الحالمة كانت عاصمة الدولة الرسولية الزاهرة، وتشتهر بمعالمها مثل قلعة القاهرة وجامع الأشرفية وجبل صبر الشامخ."
        ),
        QuizQuestion(
            id = 8,
            question = "معلم 'عرش بلقيس' ومعبد أوام وسد مأرب العظيم تقع جميعها في محافظة:",
            options = listOf("مأرب", "الجوف", "البيضاء", "صنعاء"),
            correctIndex = 0,
            explanation = "محافظة مأرب هي مهد الحضارة السبئية القديمة وعاصمة الملكة بلقيس ومقر السد الأثري الشهير الذي ورد ذكره في القرآن الكريم."
        ),
        QuizQuestion(
            id = 9,
            question = "صهاريج عدن التاريخية وقلعة صيرة تقع في أي مدينة؟",
            options = listOf("عدن", "المكلا", "الحديدة", "المخا"),
            correctIndex = 0,
            explanation = "تقع صهاريج عدن الحجرية وقلعة صيرة في مدينة عدن العريقة (ثغر اليمن الباسم) على سواحل بحر العرب وخليج عدن."
        ),
        QuizQuestion(
            id = 10,
            question = "محافظة يمنية في أقصى الشرق تحد سلطنة عُمان وتشتهر باللغة المهرية ومحمية حوف:",
            options = listOf("المهرة", "حضرموت", "شبوة", "سقطرى"),
            correctIndex = 0,
            explanation = "محافظة المهرة هي البوابة الشرقية لليمن وعاصمتها الغيضة، وتتميز بثقافتها المهرية الأصيلة وطبيعة محمية حوف الضبابية المدهشة."
        ),
        QuizQuestion(
            id = 11,
            question = "أين يقع 'جسر شهارة المعلق' الحجري التاريخي الشهير؟",
            options = listOf("محافظة عمران", "محافظة حجة", "محافظة صعدة", "محافظة المحويت"),
            correctIndex = 0,
            explanation = "جسر شهارة المعلق يقع في مديرية شهارة بمحافظة عمران، وقد بُني في القرن السابع عشر ليربط بين قمتين جبليتين شاهقتين."
        ),
        QuizQuestion(
            id = 12,
            question = "ميناء 'المخا' التاريخي المشهور عالمياً بأول تصدير للبن (الموكا) يتبع إدارياً محافظة:",
            options = listOf("تعز", "الحديدة", "لحج", "عدن"),
            correctIndex = 0,
            explanation = "ميناء ومدينة المخا التاريخية الساحلية تتبع إدارياً محافظة تعز، ومنها اشتق اسم قهوة 'الموكا' (Mocha) في جميع لغات العالم."
        )
    )

    fun getGovernorateById(id: String): Governorate? {
        return governorates.firstOrNull { it.id == id }
    }

    fun searchGovernorates(query: String): List<Governorate> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return governorates
        return governorates.filter {
            it.nameArabic.contains(q) ||
            it.nameEnglish.lowercase().contains(q) ||
            it.capital.contains(q) ||
            it.nickname.contains(q) ||
            it.landmarks.any { l -> l.contains(q) } ||
            it.keyProducts.any { p -> p.contains(q) }
        }
    }
}
