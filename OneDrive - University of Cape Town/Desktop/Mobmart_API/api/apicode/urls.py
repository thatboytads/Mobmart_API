from django.urls import path
from . import views

urlpatterns = [
    # api auth routes
    path("", views.welcome_view, name="welcome"),
    path("login", views.login_view, name="login"),
    path("register", views.register, name="register"),
    path("logout", views.logout_view, name="logout"),


    # api transaction routs
    path("create", views.CreateTransactionAPIView.as_view(),
         name="transaction_create"),

    path("list", views.ListTransactionAPIView.as_view(),
         name="transaction_list"),

    path("create_profile", views.CreateProfileAPIView.as_view(),
         name="profile_create"),

    path("list_profile", views.ListProfileAPIView.as_view(),
         name="profile_list"),

    path("update_profile", views.UpdateProfileAPIView.as_view(),
         name="profile_update"),

    path("delete_profile", views.DeleteProfileAPIView.as_view(),
         name="profile_delete"),


    # path("create_transaction", views.createTransaction,
    #      name="create_transaction"),


    # payment routes
    #     path("payment", views.PaymentView, name="payment"),
]
