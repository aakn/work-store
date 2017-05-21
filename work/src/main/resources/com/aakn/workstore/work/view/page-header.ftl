<#-- @ftlvariable name="" type="com.aakn.workstore.work.view.BaseView" -->
<div class="row">
    <div class="page-header">
        <h1>Works @ ${namespace?html}</h1>
    </div>
    <ol class="breadcrumb">
    <#if type == "index">
        <li class="active">Home</li>
    <#elseif type == "make">
        <li><a href="/${namespace}/works">Home</a></li>
        <li class="active">${make?capitalize?html}</li>
    <#else>
    </#if>
    </ol>
</div>