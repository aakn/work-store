<#-- @ftlvariable name="" type="com.aakn.workstore.work.view.IndexView" -->
<!DOCTYPE html>
<html lang="en">
<#include "head.ftl">
<body>
<div class="container">
<#include "page-header.ftl">
    <div class="row">
        <div class="col-md-4">
            <div>
                <div class="list-group">
                <#list makeNames.names as make>
                    <a href="#" class="list-group-item">${make?html}</a>
                </#list>
                </div>
            </div>
        </div>
        <div class="col-md-6">
        <#list works.works as work>
            <div class="media">
                <div class="media-left media-middle">
                    <a href="#">
                        <img class="media-object" src="${work.images.smallImage}">
                    </a>
                </div>
                <div class="media-body">
                    <h4 class="media-heading">${work.exif.make?capitalize?html}</h4>
                    Model: ${work.exif.model?html}
                </div>
            </div>
        </#list>
        </div>
    </div>
</div>
<#include "footer.ftl">
<#include "scripts.ftl">
</body>
</html>