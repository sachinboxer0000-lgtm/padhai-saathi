पढ़ाई साथी — APK बनाने के कदम (Termux + GitHub)
Termux में Android SDK चलाना बहुत मुश्किल है, इसलिए APK GitHub बनाएगा, आप Termux से सिर्फ़ फ़ाइलें भेजोगे।
1. github.com पर खाता बनाओ, नया खाली repo बनाओ (नाम: padhai-saathi) और Settings > Developer settings से एक token बनाओ (repo अनुमति)।
2. Termux में:
   pkg install git unzip -y
   unzip padhai-saathi-android.zip -d padhai && cd padhai
   git init && git add . && git commit -m "app" && git branch -M main
   git remote add origin https://github.com/आपका-नाम/padhai-saathi.git
   git push -u origin main   (password की जगह token डालना)
3. GitHub पर repo > Actions > build-apk पूरा होने दो (3-6 मिनट) > नीचे Artifacts से padhai-saathi-apk डाउनलोड करो > अंदर की app-debug.apk इंस्टॉल करो।
4. पहली बार सख़्त मोड चालू करने पर दो अनुमतियाँ माँगेगा: "दूसरे ऐप के ऊपर दिखाओ" और "Usage access"। दोनों चालू करो, फिर वापस आकर टाइमर शुरू करो।
5. फ़ोन की सेटिंग में ऐप को बैटरी "Unrestricted" कर दो, ताकि अलार्म समय पर बजे।
