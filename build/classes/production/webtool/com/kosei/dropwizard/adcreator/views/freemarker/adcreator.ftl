<#-- @ftlvariable name="" type="com.kosei.dropwizard.adcreator.views.AdCreatorView" -->
<html>
<head>
    <script>
        function doit() {
            var productImageFile = document.getElementById('productImageFile').value;
            var logoImageFile = document.getElementById('logoImageFile').value;
            var fontFile = document.getElementById('fontFile').value;

            alert(productImageFile);
            var url =  "/adcreator/create?productImageFile=" + productImageFile +"&logoImageFile="+logoImageFile+ "&fontFile="+fontFile;
            window.location.href = url;
        }
    </script>
</head>
    <body>

        <h1>Create an Image</h1>
        <form >
            <input type="button" value="Submit" onclick="doit()"><br>


            <select id="productImageFile">
                <option value="">-- product images --</option>

            <#list products as product>
                <option value="${product}">${product}</option>
            </#list>
            </select>

            <select id="logoImageFile">
                <option value="">-- logo images --</option>

            <#list logos as logo>
                <option value="${logo}">${logo}</option>
            </#list>
            </select>


            <select id="fontFile">
                <option value="">-- fonts --</option>

            <#list fonts as font>
                <option value="${font}">${font}</option>
            </#list>
            </select>



        </form>

        <br/>

        <#if adCreator.imageUrl?has_content>
        <img src="${adCreator.imageUrl}"/>
        </#if>



    </body>
</html>