(function() {

    var filename = 'https://tympanus.net/codrops/adpacks/demoad.css?' + new Date().getTime();
    var fileref = document.createElement("link");
    fileref.setAttribute("rel", "stylesheet");
    fileref.setAttribute("type", "text/css");
    fileref.setAttribute("href", filename);
    document.getElementsByTagName("head")[0].appendChild(fileref);

    let cdaSpots = ['ad1'];
    let cdaSpot = cdaSpots[Math.floor(Math.random() * cdaSpots.length)];

    switch (cdaSpot) {
        case "ad1":
            var cdaLink = 'https://ad.doubleclick.net/ddm/trackclk/N1224323.3091281BUYSELLADS/B25600467.326870981;dc_trk_aid=519823378;dc_trk_cid=157730382;dc_lat=;dc_rdid=;tag_for_child_directed_treatment=;tfua=;ltd=';
            var cdaImg = 'https://tympanus.net/codrops/wp-content/banners/mailchimp_demo.png';
            var cdaImgAlt = 'Mailchimp';
            var cdaText = "Grow sales with a smart marketing platform. ";
            break;

        /* let cdaVariations = ['v1','v2'];
         let cdaVariation = cdaVariations[Math.floor(Math.random() * cdaVariations.length)];

         switch (cdaVariation) {
             case "v1":
                 var cdaLink = 'https://ad.doubleclick.net/ddm/trackclk/N1224323.3091281BUYSELLADS/B25600467.324342596;dc_trk_aid=516737607;dc_trk_cid=157730382;dc_lat=;dc_rdid=;tag_for_child_directed_treatment=;tfua=;ltd=';
                 var cdaImg = 'https://tympanus.net/codrops/wp-content/banners/mailchimp_demo.png';
                 var cdaImgAlt = 'Mailchimp';
                 var cdaText = "Grow sales with a smart marketing platform. ";
                 break;
             case "v2":
                 var cdaLink = 'https://ad.doubleclick.net/ddm/trackclk/N1224323.3091281BUYSELLADS/B25600467.324553387;dc_trk_aid=516579785;dc_trk_cid=163822330;dc_lat=;dc_rdid=;tag_for_child_directed_treatment=;tfua=;ltd=';
                 var cdaImg = 'https://tympanus.net/codrops/wp-content/banners/mailchimp_demo.png';
                 var cdaImgAlt = 'Mailchimp';
                 var cdaText = "Drive better results with a smart marketing platform. ";
                 break;
             default:
                 var cdaLink = 'https://ad.doubleclick.net/ddm/trackclk/N1224323.3091281BUYSELLADS/B25600467.324342596;dc_trk_aid=516737607;dc_trk_cid=157730382;dc_lat=;dc_rdid=;tag_for_child_directed_treatment=;tfua=;ltd=';
                 var cdaImg = 'https://tympanus.net/codrops/wp-content/banners/mailchimp_demo.png';
                 var cdaImgAlt = 'Mailchimp';
                 var cdaText = "Grow sales with a smart marketing platform. ";
                 break;
         }*/

        case "ad2":
            var cdaLink = 'https://srv.buysellads.com/ads/long/x/TH4C2IZ3TTTTTT4FGXHN4TTTTTT6JQNPK6TTTTTTEDZABYVTTTTTTESLPVSNKSJ7537HLRSWP36FP2QYVQCI4WZ727CT';
            var cdaImg = 'https://tympanus.net/codrops/wp-content/uploads/2021/10/Shortcut260x200.png';
            var cdaImgAlt = 'Shortcut';
            var cdaText = "Shortcut is made for developers and provides speedy task management, reporting, and collaboration.";
            break;
        case "ad3":
            var cdaLink = 'https://srv.buysellads.com/ads/long/x/TCBFN4Z3TTTTTT43QYFN4TTTTTT4XOT5K6TTTTTT5DHTLYVTTTTTTALDGQVF5SB6Z3NWNMP5VJ7DKYPMKQ7UKKSECRPT';
            var cdaImg = 'https://tympanus.net/codrops/wp-content/uploads/2021/08/MWB_demo.jpg';
            var cdaImgAlt = 'Malwarebytes';
            var cdaText = "Detect threats that other cybersecurity solutions miss with Malwarebytes.";
            break;
        default:
            var cdaLink = 'https://www.elegantthemes.com/affiliates/idevaffiliate.php?id=17972_5_1_16';
            var cdaImg = 'https://tympanus.net/codrops/wp-content/banners/Divi_Carbon.jpg';
            var cdaImgAlt = 'Divi';
            var cdaText = "From our sponsor: Divi is more than just a WordPress theme, it's a completely new website building platform. Try it.";
    }

    var cda = document.createElement('div');
    cda.id = 'cdawrap';
    cda.style.display = 'none';
    cda.innerHTML = '<a href="'+cdaLink+'" class="carbon-img" target="_blank" rel="noopener"><img src="'+cdaImg+'" alt="'+cdaImgAlt+'" border="0" height="100" width="130"></a><a href="'+cdaLink+'" class="carbon-text" target="_blank" rel="noopener">'+cdaText+'</a><div class="cda-footer"><a class="carbon-poweredby" href="https://tympanus.net/codrops/advertise/" target="_blank" rel="noopener">Become a sponsor</a><span class="cda-remove" id="cda-remove">Close</span></div>';
    document.getElementsByTagName('body')[0].appendChild(cda);

    setTimeout(function() {
        cda.style.display = 'block';
    }, 1000);

    document.getElementById('cda-remove').addEventListener('click', function(e) {
        cda.style.display = 'none';
        e.preventDefault();
    });

})();