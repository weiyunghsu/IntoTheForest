package com.weiyung.intotheforest.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.source.IntoTheForestRepository

class HomeViewModel (private val repository: IntoTheForestRepository): ViewModel(){
    private val _naviToSelectedArticle = MutableLiveData<Article>()
    val naviToSelectedArticle : LiveData<Article>
        get() = _naviToSelectedArticle

    fun displayDetail(article: Article){
        _naviToSelectedArticle.value = article
    }
    fun displayDetailAll(){
        _naviToSelectedArticle.value = null
    }
//    var list = mutableListOf(
//        Article(1,"2021.01.01","2021.01.02","登四獸山",1,"Amy",
//            "https://firebasestorage.googleapis.com/v0/b/intotheforest.appspot.com/o/four_animals.jpg?alt=media&token=df229f40-7148-4ef0-807d-51890f9c3bb8",
//            "臣亮言：先帝創業未半，而中道崩殂。今天下三分，益州疲弊，此誠危急存亡之秋也。\n" +
//                    "然侍衛之臣，不懈於內；忠志之士，忘身於外者，蓋追先帝之殊遇，欲報之於陛下也。\n" +
//                    "誠宜開張聖聽，以光先帝遺德，恢弘志士之氣；不宜妄自菲薄，引喻失義，以塞忠諫之路也。\n" +
//                    "宮中府中，俱為體，陟罰臧否，不宜異同。若有作姦犯科，及為忠善者，宜付有司，論其刑賞，以昭陛下平明之治，不宜篇私，使內外異法也。\n" +
//                    "侍中、侍郎郭攸之、費褘、董允等，此皆良實，志慮忠純，是以先帝簡拔以遺陛下。愚以為宮中之事，事無大小，悉以咨之，然後施行，必能裨補闕漏，有所廣益。\n" +
//                    "將軍向寵，性行淑均，曉暢軍事，試用於昔日，先帝稱之曰「能」，是以眾議舉寵為督。\n" +
//                    "愚以為營中之事，悉以咨之，必能使行陣和睦，優劣得所。親賢臣，遠小人，此先漢所以興隆也；親小人，遠賢臣，此後漢所以傾頹也。\n" +
//                    "先帝在時，每與臣論此事，未嘗不歎息痛恨於桓、靈也。侍中、尚書、長史；參軍，此悉貞良死節之臣也，願陛下親之信之，則漢室之隆，可計日而待也。\n" +
//                    "臣本布衣，躬耕於南陽，苟全性命於亂世，不求聞達於諸侯。先帝不以臣卑鄙，猥自枉屈，三顧臣於草廬之中，諮臣以當世之事，由是感激，遂許先帝以驅馳。\n" +
//                    "後值傾覆，受任於敗軍之際，奉命於危難之間，爾來二十有一年矣！先帝知臣謹慎，故臨崩寄臣以大事也。\n" +
//                    "受命以來，夙夜憂勤，恐託付不效，以傷先帝之明。故五月渡瀘，深入不毛。\n" +
//                    "今南方已定，兵甲已足，當獎率三軍，北定中原，庶竭駑鈍，攘除奸凶，興復漢室，還於舊都；此臣所以報先帝而忠陛下之職分也。\n" +
//                    "至於斟酌損益，進盡忠言，則攸之、褘、允之任也。願陛下託臣以討賊興復之效；不效，則治臣之罪，以告先帝之靈。\n" +
//                    "若無興德之言，則戮允等，以彰其慢。陛下亦宜自課，以諮諏善道，察納雅言，深追先帝遺詔，臣不勝受恩感激。\n" +
//                    "今當遠離，臨表涕零，不知所云。"),
//        Article(2,"2021.02.01","2021.02.02","登七星山",2,"Bob",
//            "https://firebasestorage.googleapis.com/v0/b/intotheforest.appspot.com/o/seven_star.jpg?alt=media&token=f412a800-a902-444f-b3ab-26cde2a4fb7b",
//            "先帝深慮漢、賊不兩立，王業不偏安，故託臣以討賊也。以先帝之明，量臣之才，固知臣伐賊，才弱敵強也。\n" +
//                    "然不伐賊，王業亦亡。惟坐而待亡，孰與伐之？是故託臣而弗疑也。臣受命之日，寢不安席，食不甘味。思惟北征。\n" +
//                    "宜先入南。故五月渡瀘，深入不毛，並日而食；臣非不自惜也，顧王業不可得偏安於蜀都，故冒危難，以奉先帝之遺意也，而議者謂爲非計。\n" +
//                    "今賊適疲於西，又務於東，兵法乘勞，此進趨之時也。謹陳其事如左：高帝明並日月，謀臣淵深，然涉險被創，危然後安。\n" +
//                    "今陛下未及高帝，謀臣不如良、平，而欲以長策取勝，坐定天下，此臣之未解一也。\n" +
//                    "劉繇、王朗各據州郡，論安言計，動引聖人，羣疑滿腹，衆難塞胸，今歲不戰，明年不徵，使孫策坐大，遂並江東，此臣之未解二也。\n" +
//                    "曹操智計，殊絕於人，其用兵也，彷彿孫、吳，然困於南陽，險於烏巢，危於祁連，逼於黎陽，幾敗北山，殆死潼關，然後僞定一時耳。況臣才弱，而欲以不危而定之，此臣之未解三也。\n" +
//                    "曹操五攻昌霸不下，四越巢湖不成，任用李服而李服圖之，委任夏侯而夏侯敗亡，先帝每稱操爲能，猶有此失，況臣駑下，何能必勝？此臣之未解四也。\n" +
//                    "自臣到漢中，中間期年耳，然喪趙雲、陽羣、馬玉、閻芝、丁立、白壽、劉郃、鄧銅等及曲長、屯將七十餘人，突將、無前、賨叟、青羌、散騎、武騎一千餘人。\n" +
//                    "此皆數十年之內所糾合四方之精銳，非一州之所有；若複數年，則損三分之二也，當何以圖敵？此臣之未解五也。\n" +
//                    "今民窮兵疲，而事不可息；事不可息，則住與行勞費正等。而不及今圖之，欲以一州之地，與賊持久，此臣之未解六也。\n" +
//                    "夫難平者，事也。昔先帝敗軍於楚，當此時，曹操拊手，謂天下以定。然後先帝東連吳越，西取巴蜀，舉兵北征，夏侯授首，此操之失計，而漢事將成也。\n" +
//                    "然後吳更違盟，關羽毀敗，秭歸蹉跌，曹丕稱帝。凡事如是，難可逆見。臣鞠躬盡瘁，死而後已。至於成敗利鈍，非臣之明所能逆睹也。"),
//        Article(3,"2021.03.01","2021.03.02","登三貂嶺",3,"Candy",
//           "https://firebasestorage.googleapis.com/v0/b/intotheforest.appspot.com/o/three_diao.jpg?alt=media&token=3173d9db-03c3-4831-9dce-31c97c3d59ae",
//            "君不見，黃河之水天上來，奔流到海不复回。\n" +
//                    "君不見，高堂明鏡悲白發，朝如青絲暮成雪。\n" +
//                    "人生得意須盡歡，莫使金樽空對月。\n" +
//                    "天生我材必有用，千金散盡還复來。\n" +
//                    "烹羊宰牛且為樂，會須一飲三百杯。\n" +
//                    "岑夫子，丹丘生，將進酒，君莫停。\n" +
//                    "与君歌一曲，請君為我側耳听。\n" +
//                    "鐘鼓饌玉不足貴，但愿長醉不愿醒。\n" +
//                    "古來圣賢皆寂寞，惟有飲者留其名。\n" +
//                    "陳王昔時宴平樂，斗酒十千恣歡謔。\n" +
//                    "主人何為言少錢，徑須沽取對君酌。\n" +
//                    "五花馬，千金裘，呼儿將出換美酒，\n" +
//                    "与爾同銷万古愁。"),
//        Article(4,"2021.04.01","2021.04.02","登劍潭山",4,"Dylan",
//           "https://firebasestorage.googleapis.com/v0/b/intotheforest.appspot.com/o/jian_tan.jpg?alt=media&token=d27dac67-0cb7-4b21-ad65-6bd05f72c805",
//            "妾發初覆額，折花門前劇。\n" +
//                    "郎騎竹馬來，繞床弄青梅。\n" +
//                    "同居長干里，兩小無嫌猜。\n" +
//                    "十四為君婦，羞顏未嘗開。\n" +
//                    "低頭向暗壁，千喚不一回。\n" +
//                    "十五始展眉，愿同塵与灰。\n" +
//                    "常存抱柱信，豈上望夫台。\n" +
//                    "十六君遠行，瞿塘灩預堆。\n" +
//                    "五月不可触，猿聲天上哀。\n" +
//                    "門前遲行跡，一一生綠苔。\n" +
//                    "苔深不能掃，落葉秋風早。\n" +
//                    "八月蝴蝶黃，雙飛西園草。\n" +
//                    "感此傷妾心，坐愁紅顏老。\n" +
//                    "早晚下三巴，預將書報家。\n" +
//                    "相迎不道遠，直至長風沙。\n"),
//        Article(5,"2021.05.01","2021.05.02","登觀音山",5,"Emily",
//           "https://firebasestorage.googleapis.com/v0/b/intotheforest.appspot.com/o/kuan_in.jpg?alt=media&token=e5c25288-20ad-45bf-89e9-92853e1f6f6e",
//            "潯陽江頭夜送客，楓葉荻花秋瑟瑟。\n" +
//                    "主人下馬客在船，舉酒欲飲無管弦。\n" +
//                    "醉不成歡慘將別，別時茫茫江浸月。\n" +
//                    "忽聞水上琵琶聲，主人忘歸客不發。\n" +
//                    "尋聲暗問彈者誰，琵琶聲停欲語遲。\n" +
//                    "移船相近邀相見，添酒回燈重開宴。\n" +
//                    "千呼万喚始出來，猶抱琵琶半遮面。\n" +
//                    "轉軸撥弦三兩聲，未成曲調先有情。\n" +
//                    "弦弦掩抑聲聲思，似訴平生不得志。\n" +
//                    "低眉信手續續彈，說盡心中無限事。\n" +
//                    "輕攏慢捻抹复挑，初為霓裳后六么。\n" +
//                    "大弦嘈嘈如急雨，小弦切切如私語。\n" +
//                    "嘈嘈切切錯雜彈，大珠小珠落玉盤。\n" +
//                    "間關鶯語花底滑，幽咽泉流水下灘。\n" +
//                    "水泉冷澀弦凝絕，凝絕不通聲漸歇。\n" +
//                    "別有幽愁暗恨生，此時無聲胜有聲。\n" +
//                    "銀瓶乍破水漿迸，鐵騎突出刀槍鳴。\n" +
//                    "曲終收撥當心畫，四弦一聲如裂帛。\n" +
//                    "東船西舫悄無言，唯見江心秋月白。\n" +
//                    "沉吟放撥插弦中，整頓衣裳起斂容。\n" +
//                    "自言本是京城女，家在蝦蟆陵下住。\n" +
//                    "十三學得琵琶成，名屬教坊第一部。\n" +
//                    "曲罷常教善才服，妝成每被秋娘爐。\n" +
//                    "五陵年少爭纏頭，一曲紅消不知數。\n" +
//                    "鈿頭銀篦擊節碎，血色羅裙翻酒污。\n" +
//                    "今年歡笑复明年，秋月春風等閒度。\n" +
//                    "弟走從軍阿姨死，暮去朝來顏色故。\n" +
//                    "門前冷落車馬稀，老大嫁作商人婦。\n" +
//                    "商人重利輕別离，前月浮梁買茶去。\n" +
//                    "去來江口守空船，繞艙明月江水寒。\n" +
//                    "夜深忽夢少年事，夢啼妝淚紅闌干。\n" +
//                    "我聞琵琶已歎息，又聞此語重唧唧。\n" +
//                    "同是天涯淪落人，相逢何必曾相識。\n" +
//                    "我從去年辭帝京，謫居臥病潯陽城。\n" +
//                    "潯陽地僻無音樂，終歲不聞絲竹聲。\n" +
//                    "住近湓江地低濕，黃蘆苦竹繞宅生。\n" +
//                    "其間旦暮聞何物，杜鵑啼血猿哀鳴。\n" +
//                    "春江花朝秋月夜，往往取酒還獨傾。\n" +
//                    "豈無山歌与姑笛，嘔啞嘲哳難為听。\n" +
//                    "今夜聞君琵琶語，如听仙樂耳暫明。\n" +
//                    "莫辭更坐彈一曲，為君翻作琵琶行。\n" +
//                    "感我此言良久立，卻坐促弦弦轉急。\n" +
//                    "凄凄不似向前聲，滿座重聞皆掩泣。\n" +
//                    "座中泣下誰最多，江州司馬清衫濕。"),
//        Article(6,"2021.06.01","2021.06.02","四獸山遊記",6,"Fox",
//            "https://firebasestorage.googleapis.com/v0/b/intotheforest.appspot.com/o/four_animals.jpg?alt=media&token=df229f40-7148-4ef0-807d-51890f9c3bb8",
//            "昔人已乘黃鶴去，此地空余黃鶴樓。\n" +
//                    "黃鶴一去不复返，白云千載空悠悠。\n" +
//                    "晴川歷歷漢陽樹，芳草萋萋鸚鵡洲。\n" +
//                    "日暮鄉關何處是，煙波江上使人愁。\n"),
//        Article(7,"2021.07.01","2021.07.02","七星山遊記",7,"Gary",
//           "https://firebasestorage.googleapis.com/v0/b/intotheforest.appspot.com/o/seven_star.jpg?alt=media&token=f412a800-a902-444f-b3ab-26cde2a4fb7b",
//            "唧唧復唧唧，木蘭當戶織。不聞機杼聲，惟聞女嘆息。\n" +
//                    "問女何所思，問女何所憶。女亦無所思，女亦無所憶。昨夜見軍帖，可汗大點兵，軍書十二卷，卷卷有爺名。阿爺無大兒，木蘭無長兄，願爲市鞍馬，從此替爺徵。\n" +
//                    "東市買駿馬，西市買鞍韉，南市買轡頭，北市買長鞭。旦辭爺孃去，暮宿黃河邊，不聞爺孃喚女聲，但聞黃河流水鳴濺濺。旦辭黃河去，暮至黑山頭，不聞爺孃喚女聲，但聞燕山胡騎鳴啾啾。\n" +
//                    "萬里赴戎機，關山度若飛。朔氣傳金柝，寒光照鐵衣。將軍百戰死，壯士十年歸。\n" +
//                    "歸來見天子，天子坐明堂。策勳十二轉，賞賜百千強。可汗問所欲，木蘭不用尚書郎，願馳千里足，送兒還故鄉。\n" +
//                    "爺孃聞女來，出郭相扶將；阿姊聞妹來，當戶理紅妝；小弟聞姊來，磨刀霍霍向豬羊。開我東閣門，坐我西閣牀，脫我戰時袍，著我舊時裳。當窗理雲鬢，對鏡貼花黃。出門看火伴，火伴皆驚忙：同行十二年，不知木蘭是女郎。\n" +
//                    "雄兔腳撲朔，雌兔眼迷離；雙兔傍地走，安能辨我是雄雌？"),
//        Article(8,"2021.08.01","2021.08.02","三貂嶺遊記",8,"Harry",
//           "https://firebasestorage.googleapis.com/v0/b/intotheforest.appspot.com/o/three_diao.jpg?alt=media&token=3173d9db-03c3-4831-9dce-31c97c3d59ae",
//            "君不見黃河之水天上來，奔流到海不復回。\n" +
//                    "君不見高堂明鏡悲白髮，朝如青絲暮成雪。\n" +
//                    "人生得意須盡歡，莫使金樽空對月。\n" +
//                    "天生我材必有用，千金散盡還復來。\n" +
//                    "烹羊宰牛且為樂，會須一飲三百杯。\n" +
//                    "岑夫子，丹丘生，將進酒，杯莫停。\n" +
//                    "與君歌一曲，請君為我傾耳聽。\n" +
//                    "鐘鼓饌玉何足貴，但願長醉不願醒。\n" +
//                    "古來聖賢皆寂寞，唯有飲者留其名。\n" +
//                    "陳王昔時宴平樂，斗酒十千恣歡謔。\n" +
//                    "主人何為言少錢，徑須沽取對君酌。\n" +
//                    "五花馬，千金裘。\n" +
//                    "呼兒將出換美酒，與爾同銷萬古愁。"),
//        Article(9,"2021.09.01","2021.09.02","劍潭山遊記",9,"Ian",
//            "https://firebasestorage.googleapis.com/v0/b/intotheforest.appspot.com/o/jian_tan.jpg?alt=media&token=d27dac67-0cb7-4b21-ad65-6bd05f72c805",
//            "觀自在菩薩，行深般若波羅蜜多時，照見五蘊皆空，度一切苦厄。\n" +
//                    "舍利子！色不異空，空不異色；色即是空，空即是色，受想行識亦復如是。\n" +
//                    "舍利子！是諸法空相，不生不滅，不垢不淨，不增不減。\n" +
//                    "是故，空中無色，無受想行識；無眼耳鼻舌身意；無色聲香味觸法；\n" +
//                    "無眼界，乃至無意識界；無無明，亦無無明盡，乃至無老死，亦無老死盡；\n" +
//                    "無苦集滅道；無智亦無得。以無所得故，菩提薩埵。\n" +
//                    "依般若波羅蜜多故，心無罣礙；無罣礙故，無有恐怖，遠離顛倒夢想，究竟涅槃。\n" +
//                    "三世諸佛，依般若波羅蜜多故，得阿耨多羅三藐三菩提。\n" +
//                    "故知：般若波羅蜜多是大神咒，是大明咒，是無上咒，是無等等咒，能除一切苦，真實不虛。\n" +
//                    "故說般若波羅蜜多咒，即說咒曰：揭諦揭諦，波羅揭諦，波羅僧揭諦，菩提薩婆訶。"),
//        Article(10,"2021.10.01","2021.10.02","觀音山遊記",10,"Jay",
//            "https://firebasestorage.googleapis.com/v0/b/intotheforest.appspot.com/o/kuan_in.jpg?alt=media&token=e5c25288-20ad-45bf-89e9-92853e1f6f6e",
//            "黎明即起，灑掃庭除，要內外整潔。既昏便息，關鎖門戶，必親自檢點。一粥一飯，當思來處不易；半絲半縷，恒念物力維艱。宜未雨而綢繆，毋臨渴而掘井。自奉必須儉約，宴客切勿留連。器具質而潔，瓦缶勝金玉；飲食約而精，園蔬勝珍饈。勿營華屋，勿謀良田。\n" +
//                    "三姑六婆，實淫盜之媒；婢美妾嬌，非閨房之福。童僕勿用俊美，妻妾切忌豔妝。祖宗雖遠，祭祀不可不誠；子孫雖愚，經書不可不讀。居身務期質樸，教子要有義方。勿貪意外之財，勿飲過量之酒。\n" +
//                    "與肩挑貿易，勿佔便宜；見貧苦親鄰，須多溫恤。刻薄成家，理無久享；倫常乖舛，立見消亡。兄弟叔侄，須分多潤寡；長幼內外，宜法肅辭嚴。聽婦言，乖骨肉，豈是丈夫？重貲財，薄父母，不成人子。嫁女擇佳婿，毋索重聘；娶媳求淑女，毋計厚奩。\n" +
//                    "見富貴而生讒容者，最可恥；遇貧窮而作驕態者，賤莫甚。居家戒爭訟，訟則終凶；處世戒多言，言多必失。毋恃勢力而凌逼孤寡，勿貪口腹而恣殺生禽。乖僻自是，悔誤必多；頹惰自甘，家道難成。狎昵惡少，久必受其累；屈志老成，急則可相依。輕聽發言，安知非人之譖訴，當忍耐三思；因事相爭，安知非我之不是，須平心暗想。\n" +
//                    "施惠勿念，受恩莫忘。凡事當留餘地，得意不宜再往。人有喜慶，不可生妒忌心；人有禍患，不可生喜幸心。善欲人見，不是真善；惡恐人知，便是大惡。見色而起淫心，報在妻女；匿怨而用暗箭，禍延子孫。\n" +
//                    "家門和順，雖饔飧不繼，亦有餘歡；國課早完，即囊橐無餘，自得至樂。讀書志在聖賢，非徒科第；為官心存君國，豈計身家？守分安命，順時聽天。為人若此，庶乎近焉。")
//    )
}