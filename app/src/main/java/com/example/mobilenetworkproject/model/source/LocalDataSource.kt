package com.example.mobilenetworkproject.model.source

import com.example.mobilenetworkproject.R
import com.example.mobilenetworkproject.model.entity.StudentModel
import com.example.mobilenetworkproject.model.entity.TouristPlaceModel

object LocalDataSource {
    fun selectTouristPlace(): Array<TouristPlaceModel> {
        val tp1 = TouristPlaceModel(
            id = 0,
            name = "IMAMZADEH SALEH",
            country = "IRAN",
            star = 4.3F,
            description = "If you have extra time in Tehran, consider visiting the beautiful Imamzadeh Saleh, also known as Tajrish Mosque. This site is where Saleh, the son of Musa al-Kadhim (the seventh Shia Imam) rests. You’ll witness some truly stunning tilework on the mosque’s minarets and dome, and the interior is decorated with brilliant mirror work, something that’s quite common in Iranian shrines. You can also stop by the colorful Tajrish Bazaar nearby for some souvenirs and more glimpses of local life.",
            imageResourceId = R.drawable.tp1
        )
        val tp2 = TouristPlaceModel(
            id = 1,
            name = "NASIR OL MOLK MOSQUE",
            country = "IRAN",
            star = 4.1F,
            description = "Shiraz is filled with gorgeous mosques, but the one that takes the prize is hands down the spectacular Nasir Ol Molk Mosque (also commonly known as the Pink Mosque). Completed in 1888 during the Qajar dynasty, this mosque is famous for its multi-colored stained-glass windows which allow the morning sun to play a glorious light show on the floor. For this reason, Nasir Ol Molk Mosque is very popular among photographers and is definitely one of the most beautiful places in Iran. You simply cannot visit Iran without seeing the rainbow colors and magical light reflections at this mosque! It’s something truly unique and really feels as though you’re stepping inside a kaleidoscope.\n" +
                    "Nasir Ol Molk Mosque is stunning from the outside too. It has a marvelous courtyard with a pond, and its vibrant tilework is a symbol of western influences on Iranian architecture in the 19th century. In fact, a lot of the tiles used to build this mosque (and many other Qajar-era buildings) were imported from France, Germany, and the UK. Depicted on them are typical European art elements such as images of landscape, women, and European architecture. You can easily spend hours admiring the details of the tilework both on the interior and exterior of this mosque!",
            imageResourceId = R.drawable.tp2
        )
        val tp3 = TouristPlaceModel(
            id = 2,
            name = "SAYYED ALAEDDIN HOSSEIN SHRINE",
            country = "IRAN",
            star = 4.1F,
            description = "About a 10-minute walk from the famous Shah Cheragh Shrine is a much lesser-known shrine: the Sayyed Alaeddin Hossein Shrine. Built during the Safavid dynasty (which ruled Iran from 1501 to 1736), this site is truly a hidden gem; a place without the tourist crowds of Shah Cheragh Shrine, but equally splendid and impressive. While it may look like just another mosque from the outside, the interior of Sayyed Alaeddin Hossein Shrine is filled with millions of sparkling glass shards of all different colors, and you can admire the intricacy of the mirror work in peace and quiet — an authentic and less touristy experience than the previous shrine.",
            imageResourceId = R.drawable.tp3
        )
        val tp4 = TouristPlaceModel(
            id = 3,
            name = "ERAM GARDEN",
            country = "IRAN",
            star = 4.1F,
            description = "Shiraz isn’t just filled with stunning mosques and shrines, it’s also home to one of the most beautiful Persian gardens in Iran \u2060— the UNESCO World Heritage Site of Eram Garden. In the Quran, ‘Eram’ refers to a paradise for the blessed, and strolling through this garden, that feeling becomes increasingly palpable. You’ll be surrounded by over 45 species of plants, 200 species of roses, and countless fruit trees, including a famous 3,000-year-old cypress tree. With the sound of birds chirping and the fresh smell of blossoms all around you, it’s hard to not feel relaxed and at peace in this enchanting place.\n" +
                    "No one knows when exactly Eram Garden was built, but it’s said to have been completed in the 13th century during the Seljuk dynasty. It was then passed down and restored multiple times before being handed to the University of Shiraz, which owns the garden today. On the majestic palace in front of the pool, you can also spot tiles inscribed with poems by the famous Persian poet Hafez.",
            imageResourceId = R.drawable.tp4
        )
        val tp5 = TouristPlaceModel(
            id = 4,
            name = "MAHARLOO LAKE",
            country = "IRAN",
            star = 4.1F,
            description = "Don’t leave Shiraz without visiting the stunning Maharloo Lake, also known as the Pink Lake due to the amount of red tide in its salty water. This fascinating natural wonder is just a one-hour drive from Shiraz and totally worth the excursion if you want to witness the beautiful landscapes surrounding the city.\n" +
                    "The best time to visit this salt lake is between the months of July and September, when the water from the lake is more likely to evaporate, making the pink hues more intense. For the same reasons, the lake is likely to be less pink during the rainy season (April to June). Don’t forget your camera, as this is definitely one of the most beautiful places in Iran.",
            imageResourceId = R.drawable.tp5
        )
        val tp6 = TouristPlaceModel(
            id = 5,
            name = "PERSEPOLIS",
            country = "IRAN",
            star = 4.1F,
            description = "Located around 60 km (37 miles) northeast of Shiraz is a place of incredible historic value: the UNESCO site of Persepolis, the glorious ceremonial capital of the Achaemenid Empire (550 – 330 BC) and a great source of pride for Iranians. Taking a day trip to explore the ancient ruins of this city is one of the top things to do in Iran. Walking among the pillars, arches, and remains of what used to be majestic palaces, you’re likely to feel incredibly small and humbled by the vast history of ancient Persia.\n" +
                    "Persepolis was believed to have been built by Darius I in around 518 BC and construction took around 120 years. Darius I was known to be a very kind king who regularly paid the construction workers and treated them very well, which was unusual for kings at the time. To this day, the primary function of Persepolis remains a mystery, although it’s speculated to have been a large ceremonial complex that was only occupied seasonally.\n" +
                    "Unfortunately, Alexander the Great invaded Achaemenid Persia in 330 BC and destroyed Persepolis as a drunken act of revenge. After burning down the city, he used 5,000 camels and 20,000 mules to carry all the gold and silver he found there. Persepolis was eventually excavated in the 1930s by a French archeologist.",
            imageResourceId = R.drawable.tp6
        )
        val tp7 = TouristPlaceModel(
            id = 6,
            name = "NAQSH-E ROSTAM",
            country = "IRAN",
            star = 4.1F,
            description = "Only a 10-minute drive from Persepolis is Naqsh-e Rostam, the royal necropolis of the Achaemenid Empire. Being so close to Persepolis, your taxi driver or tour guide would usually include this place in your day trip itinerary, and it’s definitely worth stopping here to admire the four majestic rock-cut tombs of ancient Persian kings.\n" +
                    "Historians are still debating about which kings were buried inside the tombs; they’re only sure that one of them is the king who built Persepolis \u2060— Darius I, but the other three are speculated to be Darius II, Artaxerxes I, and Xerxes I. Cut into the cliff faces above each tomb are carvings depicting the kings as god-like figures. There are also other carvings that portray battles won by the Achaemenid Empire. Perhaps the most interesting one of them all, though, is a carving so ancient that it seems to suggest this site was used even before Achaemenid Persia existed.",
            imageResourceId = R.drawable.tp7
        )
        val tp8 = TouristPlaceModel(
            id = 7,
            name = "RAYEN CASTLE",
            country = "IRAN",
            star = 4.1F,
            description = "Start your journey in Kerman by visiting the second-largest adobe castle in the world \u2060— the magnificent Rayen Castle. Built in the Sassanid era (224 – 651 AD), this majestic structure rising gloriously out of the desert will take your breath away and leave you in absolute awe. It’s a must-see during your time in Iran! The castle is also really well-preserved despite the natural disasters it had to endure. It’s said that around 5,000 people lived in this citadel up until roughly 150 years ago, and the current structure you see has been built over the ruins of an older fortress.\n" +
                    "Rayen Castle was divided into three parts: a section for the kings, a section for the lords and the wealthy, and a section for the ordinary people. Walls and towers separated each of these areas. The castle was well-situated on a major trade route and was therefore a popular hub for caravans and merchants. There were also a lot of workshops here with people making guns, knives, and swords. Today, you can still see some of them by the entrance.",
            imageResourceId = R.drawable.tp8
        )
        val tp9 = TouristPlaceModel(
            id = 8,
            name = "YAZD OLD TOWN",
            country = "IRAN",
            star = 4.1F,
            description = "The UNESCO town of Yazd is one of the most ancient cities on earth, and it’s definitely worth spending at least two days discovering its unique charms. Wandering around the historical Old Town is the perfect way to start exploring this city. You’ll be immediately drawn in by its quiet and laid-back atmosphere, and will also feel as though you’re traveling back in time.\n" +
                    "The Old Town is filled with mudbrick walls, adobe houses, hidden courtyards, splendid mosques, rooftops with views, and tons of wind towers (Yazd is actually nicknamed “City of Windcatchers”). It’s quite easy to get lost in the Old Town; the countless narrow alleys will make you feel as though you’re walking inside a maze, but it’s honestly a maze you wouldn’t want to leave!",
            imageResourceId = R.drawable.tp9
        )
        val tp10 = TouristPlaceModel(
            id = 9,
            name = "MOLLABASHI HOUSE",
            country = "IRAN",
            star = 4.1F,
            description = "One place you absolutely have to visit during your time in Isfahan is the spectacular Mollabashi House (also known as Motamedi House), the most breathtaking historical mansion I’ve ever stepped foot in. This house gives you a really good idea of how the wealthy in Iran used to live during the Zand (1751 – 1779) and Qajar (1789 – 1925) eras.\n" +
                    "Mollabashi was the famous 19th-century astronomer of the Shah and used to live in this house, but much of what you see today is thanks to the Motamedi family, who acquired this place in 2000 and spent 7 years restoring it. You can easily spend hours admiring the gorgeous details and patterns that decorate the rooms and courtyards of this historical house. Make sure to visit the fantastic guest room during the day to see the unbelievable colors and lights radiating from the stained-glass windows — one of the most beautiful sights you can witness in Iran!\n" +
                    "Exploring this house can also feel like an escape room experience as there are tons of stylish rooms hidden behind a chain of doors, and you can try each door to see which ones open (some are locked). It’s honestly such a unique experience, and the best part is that most tourists don’t visit this place, meaning you get to have the mirrored walls, stucco decorations, and elegant ceilings almost all to yourself.",
            imageResourceId = R.drawable.tp10
        )
        return arrayOf(tp1, tp2, tp3, tp4, tp5, tp6, tp7, tp8, tp9, tp10)
    }
    fun selectStudents(): Array<StudentModel>{
        val sm1 = StudentModel(
            name = "Mohammad Mahdi Soori",
            emailAddress = "mohammadmahdisoori@gmail.com",
            studentID = "96521263"
        )
        val sm2 = StudentModel(
            name = "Hamidreza Azarbad",
            emailAddress = "hamidreza.azarbad77@gmail.com",
            studentID = "96521002"
        )
        return arrayOf(sm1, sm2)
    }
}