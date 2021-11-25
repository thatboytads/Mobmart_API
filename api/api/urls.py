from django.conf import settings
from django.conf.urls.static import static
from django.contrib.staticfiles.urls import staticfiles_urlpatterns

from django.urls import path


from django.conf.urls import include, url
from django.contrib import admin

from testapp.views import PaymentView

admin.autodiscover()

urlpatterns = [
    url(r"^admin/", admin.site.urls),
    path("", include("apicode.urls")),
    path("payment", PaymentView.as_view(), name="payment"),
    url(r"^", include("django_pesapal.urls")),
]


if settings.DEBUG:
    urlpatterns += staticfiles_urlpatterns()

    urlpatterns += static(settings.STATIC_URL,
                          document_root=settings.STATIC_ROOT)
    urlpatterns += static(settings.MEDIA_URL,
                          document_root=settings.MEDIA_ROOT)


# from django.conf import settings
# from django.conf.urls.static import static
# from django.contrib.staticfiles.urls import staticfiles_urlpatterns


# from django.contrib import admin
# from django.urls import path, include
# from django.conf.urls import url


# urlpatterns = [
#     url(r'^payments/', include('django_pesapal.urls')),

#     path('admin/', admin.site.urls),
#     path("", include("apicode.urls"))
# ]


# if settings.DEBUG:
#     urlpatterns += staticfiles_urlpatterns()

#     urlpatterns += static(settings.STATIC_URL,
#                           document_root=settings.STATIC_ROOT)
#     urlpatterns += static(settings.MEDIA_URL,
#                           document_root=settings.MEDIA_ROOT)
