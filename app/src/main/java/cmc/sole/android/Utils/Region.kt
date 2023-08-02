package cmc.sole.android.Utils

enum class Region {
    S00, // 서울 전체
    S01, // 강남구
    S02, // 강동구
    S03, // 강북구
    S04, // 강서구
    S05, // 관악구
    S06, // 광진구
    S07, // 구로구
    S08, // 금천구
    S09, // 노원구
    S10, // 도봉구
    S11, // 동대문구
    S12, // 동작구
    S13, // 마포구
    S14, // 서대문구
    S15, // 서초구
    S16, // 성동구
    S17, // 성북구
    S18, // 송파구
    S19, // 양천구
    S20, // 영등포구
    S21, // 용산구
    S22, // 은평구
    S23, // 종로구
    S24, // 중구
    S25, // 중랑구

    B00,    // 부산 전체
    B01,    // 강서구
    B02,    // 금정구
    B03,    // 기장군
    B04,    // 남구
    B05,    // 동구
    B06,    // 동래구
    B07,    // 부산진구
    B08,    // 북구
    B09,    // 사상구
    B10,    // 사하구
    B11,    // 서구
    B12,    // 수영구
    B13,    // 연제구
    B14,    // 영도구
    B15,    // 중구
    B16,    // 해운대구

    D00,    // 대구 전체
    D01,    // 남구
    D02,    // 달서구
    D03,    // 달성군
    D04,    // 동구
    D05,    // 북구
    D06,    // 서구
    D07,    // 수성구
    D08,    // 중구

    I00,    // 인천 전체
    I01,    // 강화군
    I02,    // 계양구
    I03,    // 남동구
    I04,    // 동구
    I05,    // 미추홀구
    I06,    // 부평구
    I07,    // 서구
    I08,    // 연수구
    I09,    // 옹진군
    I10,    // 중구

    G00,    // 광주 전체
    G01,    // 광산구
    G02,    // 남구
    G03,    // 동구
    G04,    // 북구
    G05,    // 서구

    DJ00,    // 대전 전체
    DJ01,    // 대덕구
    DJ02,    // 동구
    DJ03,    // 서구
    DJ04,    // 유성구
    DJ05,    // 중구

    U00,    // 울산 전체
    U01,    // 남구
    U02,    // 동구
    U03,    // 북구
    U04,    // 울주군
    U05,    // 중구

    SGG,    // 세종 전체

    K00,    // 경기 전체
    K01,    // 가평군
    K02,    // 고양시
    K03,    // 과천시
    K04,    // 광명시
    K05,    // 광주시
    K06,    // 구리시
    K07,    // 군포시
    K08,    // 김포시
    K09,    // 남양주시
    K10,    // 동두천시
    K11,    // 부천시
    K12,    // 성남시
    K13,    // 수원시
    K14,    // 시흥시
    K15,    // 안산시
    K16,    // 안성시
    K17,    // 안양시
    K18,    // 양주시
    K19,    // 양평군
    K20,    // 여주시
    K21,    // 연천군
    K22,    // 오산시
    K23,    // 용인시
    K24,    // 의왕시
    K25,    // 의정부시
    K26,    // 이천시
    K27,    // 파주시
    K28,    // 평택시
    K29,    // 포천시
    K30,    // 하남시
    K31,    // 화성시

    KW00,    // 강원 전체
    KW01,    // 강릉시
    KW02,    // 고성군
    KW03,    // 동해시
    KW04,    // 삼척시
    KW05,    // 속초시
    KW06,    // 양구군
    KW07,    // 양양군
    KW08,    // 영월군
    KW09,    // 원주시
    KW10,    // 인제군
    KW11,    // 정선군
    KW12,    // 철원군
    KW13,    // 춘천시
    KW14,    // 태백시
    KW15,    // 평창군
    KW16,    // 홍천군
    KW17,    // 화천군
    KW18,    // 횡성군

    CB00,    // 충북 전체
    CB01,    // 괴산군
    CB02,    // 단양군
    CB03,    // 보은군
    CB04,    // 영동군
    CB05,    // 옥천군
    CB06,    // 음성군
    CB07,    // 제천시
    CB08,    // 증평군
    CB09,    // 진천군
    CB10,    // 청주시
    CB11,    // 충주시

    CN00,    // 충남 전체
    CN01,    // 계룡시
    CN02,    // 공주시
    CN03,    // 금산군
    CN04,    // 논산시
    CN05,    // 당진시
    CN06,    // 보령시
    CN07,    // 부여군
    CN08,    // 서산시
    CN09,    // 서천군
    CN10,    // 아산시
    CN11,    // 예산군
    CN12,    // 천안시
    CN13,    // 청양군
    CN14,    // 태안군
    CN15,    // 홍성군

    JB00,    // 전북 전체
    JB01,    // 고창군
    JB02,    // 군산시
    JB03,    // 김제시
    JB04,    // 남원시
    JB05,    // 무주군
    JB06,    // 부안군
    JB07,    // 순창군
    JB08,    // 완주군
    JB09,    // 익산시
    JB10,    // 임실군
    JB11,    // 장수군
    JB12,    // 전주시
    JB13,    // 정읍시
    JB14,    // 진안군

    JN00,    // 전남 전체
    JN01,    // 강진군
    JN02,    // 고흥군
    JN03,    // 곡성군
    JN04,    // 광양시
    JN05,    // 구례군
    JN06,    // 나주시
    JN07,    // 담양군
    JN08,    // 목포시
    JN09,    // 무안군
    JN10,    // 보성군
    JN11,    // 순천시
    JN12,    // 신안군
    JN13,    // 여수시
    JN14,    // 영광군
    JN15,    // 영암군
    JN16,    // 완도군
    JN17,    // 장성군
    JN18,    // 장흥군
    JN19,    // 진도군
    JN20,    // 함평군
    JN21,    // 해남군
    JN22,    // 화순군

    GB00,    // 경북 전체
    GB01,    // 경산시
    GB02,    // 경주시
    GB03,    // 고령군
    GB04,    // 구미시
    GB05,    // 군위군
    GB06,    // 김천시
    GB07,    // 문경시
    GB08,    // 봉화군
    GB09,    // 상주시
    GB10,    // 성주군
    GB11,    // 안동시
    GB12,    // 영덕군
    GB13,    // 영양군
    GB14,    // 영주시
    GB15,    // 영천시
    GB16,    // 예천군
    GB17,    // 울릉군
    GB18,    // 울진군
    GB19,    // 의성군
    GB20,    // 청도군
    GB21,    // 청송군
    GB22,    // 칠곡군
    GB23,    // 포항시

    GN00,    // 경남 전체
    GN01,    // 거제시
    GN02,    // 거창군
    GN03,    // 고성군
    GN04,    // 김해시
    GN05,    // 남해군
    GN06,    // 밀양시
    GN07,    // 사천시
    GN08,    // 산청군
    GN09,    // 양산시
    GN10,    // 의령군
    GN11,    // 진주시
    GN12,    // 창녕군
    GN13,    // 창원시
    GN14,    // 통영시
    GN15,    // 하동군
    GN16,    // 함안군
    GN17,    // 함양군
    GN18,    // 합천군

    JJ00,    // 제주 전체
    JJ01,    // 서귀포시
    JJ02,    // 제주시
}

fun returnRegionCode(region: String): Region {
    // MEMO: 서울
    if (region == "서울 전체") return Region.S00
    else if (region == "강남구") return Region.S01
    else if (region == "강동구")  return Region.S02
    else if (region == "강북구") return Region.S03
    else if (region == "서울 강서구")return Region.S04
    else if (region == "관악구") return Region.S05
    else if (region == "광진구") return Region.S06
    else if (region == "구로구") return Region.S07
    else if (region == "금천구") return Region.S08
    else if (region == "노원구") return Region.S09
    else if (region == "도봉구") return Region.S10
    else if (region == "동대문구") return Region.S11
    else if (region == "동작구") return Region.S12
    else if (region == "마포구") return Region.S13
    else if (region == "서대문구") return Region.S14
    else if (region == "서초구") return Region.S15
    else if (region == "성동구") return Region.S16
    else if (region == "성북구") return Region.S17
    else if (region == "송파구") return Region.S18
    else if (region == "양천구") return Region.S19
    else if (region == "영등포구") return Region.S20
    else if (region == "용산구") return Region.S21
    else if (region == "은평구") return Region.S22
    else if (region == "종로구") return Region.S23
    else if (region == "중구") return Region.S24
    else if (region == "중랑구") return Region.S25

    else if (region == "부산 전체") return Region.B00
    else if (region == "부산 강서구") return Region.B01
    else if (region == "금정구") return Region.B02
    else if (region == "기장군") return Region.B03
    else if (region == "남구") return Region.B04
    else if (region == "동구") return Region.B05
    else if (region == "동래구") return Region.B06
    else if (region == "부산진구") return Region.B07
    else if (region == "북구") return Region.B08
    else if (region == "사상구") return Region.B09
    else if (region == "사하구") return Region.B10
    else if (region == "서구") return Region.B11
    else if (region == "수영구") return Region.B12
    else if (region == "연제구") return Region.B13
    else if (region == "영도구") return Region.B14
    else if (region == "중구") return Region.B15
    else if (region == "해운대구") return Region.B16

    else if (region == "대구 전체") return Region.D00
    else if (region == "대구 남구") return Region.D01
    else if (region == "대구 달서구") return Region.D02
    else if (region == "대구 달성군") return Region.D03
    else if (region == "대구 동구") return Region.D04
    else if (region == "대구 북구") return Region.D05
    else if (region == "대구 서구") return Region.D06
    else if (region == "대구 수성구") return Region.D07
    else if (region == "대구 중구") return Region.D08

    else if (region == "인천 전체") return Region.I00
    else if (region == "인천 강화군") return Region.I01
    else if (region == "인천 계양구") return Region.I02
    else if (region == "인천 남동구") return Region.I03
    else if (region == "인천 동구") return Region.I04
    else if (region == "인천 미추홀구") return Region.I05
    else if (region == "인천 부평구") return Region.I06
    else if (region == "인천 서구") return Region.I07
    else if (region == "인천 연수구") return Region.I08
    else if (region == "인천 옹진군") return Region.I09
    else if (region == "인천 중구") return Region.I10

    else if (region == "광주 전체") return Region.G00
    else if (region == "광주 광산구") return Region.G01
    else if (region == "광주 남구") return Region.G02
    else if (region == "광주 동구") return Region.G03
    else if (region == "광주 북구") return Region.G04
    else if (region == "광주 서구") return Region.G05

    else if (region == "대전 전체") return Region.DJ00
    else if (region == "대전 대덕구") return Region.DJ01
    else if (region == "대전 동구") return Region.DJ02
    else if (region == "대전 서구") return Region.DJ03
    else if (region == "대전 유성구") return Region.DJ04
    else if (region == "대전 중구") return Region.DJ05

    else if (region == "울산 전체") return Region.U00
    else if (region == "울산 남구") return Region.U01
    else if (region == "울산 동구") return Region.U02
    else if (region == "울산 북구") return Region.U03
    else if (region == "울산 울주군") return Region.U04
    else if (region == "울산 중구") return Region.U05

    else if (region == "세종 전체") return Region.SGG

    else if (region == "경기 전체") return Region.K00
    else if (region == "경기 가평군")  return Region.K01
    else if (region == "경기 고양시") return Region.K02
    else if (region == "경기 과천시")return Region.K03
    else if (region == "경기 광명시") return Region.K04
    else if (region == "경기 광주시") return Region.K05
    else if (region == "경기 구리시") return Region.K06
    else if (region == "경기 군포시") return Region.K07
    else if (region == "경기 김포시") return Region.K08
    else if (region == "경기 남양주시") return Region.K09
    else if (region == "경기 동두천시") return Region.K10
    else if (region == "경기 부천시") return Region.K11
    else if (region == "경기 성남시") return Region.K12
    else if (region == "경기 수원시") return Region.K13
    else if (region == "경기 시흥시") return Region.K14
    else if (region == "경기 안산시") return Region.K15
    else if (region == "경기 안성시") return Region.K16
    else if (region == "경기 안양시") return Region.K17
    else if (region == "경기 양주시") return Region.K18
    else if (region == "경기 양평군") return Region.K19
    else if (region == "경기 여주시") return Region.K20
    else if (region == "경기 연천군") return Region.K21
    else if (region == "경기 오산시") return Region.K22
    else if (region == "경기 용인시") return Region.K23
    else if (region == "경기 의왕시") return Region.K24
    else if (region == "경기 의정부시") return Region.K25
    else if (region == "경기 이천시") return Region.K26
    else if (region == "경기 파주시") return Region.K27
    else if (region == "경기 평택시") return Region.K28
    else if (region == "경기 포천시") return Region.K29
    else if (region == "경기 하남시") return Region.K30
    else if (region == "경기 화성시") return Region.K31

    else if (region == "강원 전체") return Region.KW00
    else if (region == "강원 강릉시")  return Region.KW01
    else if (region == "강원 고성군") return Region.KW02
    else if (region == "강원 동해시")return Region.KW03
    else if (region == "강원 삼척시") return Region.KW04
    else if (region == "강원 속초시") return Region.KW05
    else if (region == "강원 양구군") return Region.KW06
    else if (region == "강원 양양군") return Region.KW07
    else if (region == "강원 영월군") return Region.KW08
    else if (region == "강원 원주시") return Region.KW09
    else if (region == "강원 인제군") return Region.KW10
    else if (region == "강원 정선군") return Region.KW11
    else if (region == "강원 철원군") return Region.KW12
    else if (region == "강원 춘천시") return Region.KW13
    else if (region == "강원 태백시") return Region.KW14
    else if (region == "강원 평창군") return Region.KW15
    else if (region == "강원 홍천군") return Region.KW16
    else if (region == "강원 화천군") return Region.KW17
    else if (region == "강원 횡성군") return Region.KW18

    else if (region == "충북 전체") return Region.CB00
    else if (region == "충북 괴산군")  return Region.CB01
    else if (region == "충북 단양군") return Region.CB02
    else if (region == "충북 보은군")return Region.CB03
    else if (region == "충북 영동군") return Region.CB04
    else if (region == "충북 옥천군") return Region.CB05
    else if (region == "충북 음성군") return Region.CB06
    else if (region == "충북 제천시") return Region.CB07
    else if (region == "충북 증평군") return Region.CB08
    else if (region == "충북 진천군") return Region.CB09
    else if (region == "충북 청주시") return Region.CB10
    else if (region == "충북 충주시") return Region.CB11

    else if (region == "충남 전체") return Region.CN00
    else if (region == "충남 계룡시")  return Region.CN01
    else if (region == "충남 공주시") return Region.CN02
    else if (region == "충남 금산군")return Region.CN03
    else if (region == "충남 논산시") return Region.CN04
    else if (region == "충남 당진시") return Region.CN05
    else if (region == "충남 보령시") return Region.CN06
    else if (region == "충남 부여군") return Region.CN07
    else if (region == "충남 서산시") return Region.CN08
    else if (region == "충남 서천군") return Region.CN09
    else if (region == "충남 아산시") return Region.CN10
    else if (region == "충남 예산군") return Region.CN11
    else if (region == "충남 천안시") return Region.CN12
    else if (region == "충남 청양군") return Region.CN13
    else if (region == "충남 태안군") return Region.CN14
    else if (region == "충남 홍성군") return Region.CN15

    else if (region == "전북 전체") return Region.JB00
    else if (region == "전북 고창군")  return Region.JB01
    else if (region == "전북 군산시") return Region.JB02
    else if (region == "전북 김제시")return Region.JB03
    else if (region == "전북 남원시") return Region.JB04
    else if (region == "전북 무주군") return Region.JB05
    else if (region == "전북 부안군") return Region.JB06
    else if (region == "전북 순창군") return Region.JB07
    else if (region == "전북 완주군") return Region.JB08
    else if (region == "전북 익산시") return Region.JB09
    else if (region == "전북 임실군") return Region.JB10
    else if (region == "전북 장수군") return Region.JB11
    else if (region == "전북 전주시") return Region.JB12
    else if (region == "전북 정읍시") return Region.JB13
    else if (region == "전북 진안군") return Region.JB14

    else if (region == "제주 전체") return Region.JJ00
    else if (region == "제주 서귀포시")  return Region.JJ01
    else if (region == "제주 제주시") return Region.JJ02

    else return Region.JJ00
}












