<#-- @ftlvariable name="" type="com.aakn.workstore.work.view.IndexView" -->
<!DOCTYPE html>
<html lang="en">
<#include "head.ftl">
<body>
<div class="container">
    <div class="row">
        <#include "page-header.ftl">
        <ol class="breadcrumb">
            <li class="active">Home</li>
        </ol>
    </div>
    <div class="row">
        <div class="col-md-4">
            <div>
                <div class="list-group">
                <#list makeNames.names as make>
                    <a href="/${namespace}/works/make/${make}"
                       class="list-group-item">${make?capitalize?html}</a>
                </#list>
                </div>
            </div>
        </div>
        <div class="col-md-6">
        <#include "thumbnails.ftl">
        </div>
    </div>
</div>
<#include "footer.ftl">
<#include "scripts.ftl">
</body>
</html>