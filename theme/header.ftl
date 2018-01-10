<#-- @ftlvariable name="rootPath" type="String" -->
<#-- @ftlvariable name="pageTitle" type="String" -->

<style>
    #spring-logo {
        background: url(${rootPath}/images/spring-logo.png) -1px -1px no-repeat;
    }

    #spring-logo span {
        background: url(${rootPath}/images/spring-logo.png) -1px -48px no-repeat;
    }
</style>

<header>
    <div class="container">
        <a id="spring-logo"
           href="${rootPath}"><span></span></a>
    </div>
</header>

<#if pageTitle??>
<div class="container">
    <h1>${pageTitle}</h1>
</div>
</#if>
