# import json
from django.contrib.auth import authenticate, login, logout
from django.db import IntegrityError
from django.db.models.query import QuerySet
from django.http import JsonResponse
from django.shortcuts import HttpResponse
from django.urls import reverse
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth.decorators import login_required

from rest_framework.generics import ListAPIView
from rest_framework.generics import CreateAPIView
from rest_framework.generics import DestroyAPIView
from rest_framework.generics import UpdateAPIView


from django_pesapal.views import PaymentRequestMixin
from django.views.generic import TemplateView


from .serializers import TransactionsSerializer, ProfileSerializer
from .models import User, Transactions, Vendor


def welcome_view(request):
    return HttpResponse("<h1>Welcome to Mobmart api</h1>")


def login_view(request):
    if request.method == "POST":

        # Attempt to sign user in
        email = request.POST["email"]
        password = request.POST["password"]
        user = authenticate(request, username=email, password=password)

        # Check if authentication successful
        if user is not None:
            login(request, user)
            return JsonResponse({
                "logged in": True
            }, status=200)

        else:
            return JsonResponse({
                "logged in": False,
                "error": "user not found!"
            }, status=400)

    return JsonResponse({
        "logged in": False,
        "error": "endpoint only accept post request"
    }, status=400)


def logout_view(request):
    logout(request)
    return JsonResponse({
        "logged out": True
    }, status=200)


def register(request):
    if request.method == "POST":
        email = request.POST["email"]

        # Ensure password matches confirmation
        password = request.POST["password"]
        confirmation = request.POST["confirmation"]
        if password != confirmation:
            return JsonResponse({
                "registered": False,
                "error": "Password don't match"
            }, status=400)

        # Attempt to create new user
        try:
            user = User.objects.create_user(email, email, password)
            user.save()
        except IntegrityError as e:
            print(e)

            return JsonResponse({
                "registered": False,
                "error": "Email already taken!"
            }, status=400)

        login(request, user)
        return JsonResponse({
            "registered": True
        }, status=200)

    return JsonResponse({
        "registered": False,
        "error": "endpoint only accept post request"
    }, status=400)


class CreateTransactionAPIView(CreateAPIView):
    """This endpoint allows for creation of a transaction"""
    queryset = Transactions.objects.all()
    serializer_class = TransactionsSerializer


class ListTransactionAPIView(ListAPIView):
    """This endpoint list all transactions"""
    queryset = Transactions.objects.all()
    serializer_class = TransactionsSerializer


class CreateProfileAPIView(CreateAPIView):
    """This endpoint allows for creation of a transaction"""
    queryset = User.objects.all()
    serializer_class = ProfileSerializer


class ListProfileAPIView(ListAPIView):
    """This endpoint allows for creation of a transaction"""
    queryset = User.objects.all()
    serializer_class = ProfileSerializer


class UpdateProfileAPIView(UpdateAPIView):
    """This endpoint allows for creation of a transaction"""
    queryset = User.objects.all()
    serializer_class = ProfileSerializer


class DeleteProfileAPIView(DestroyAPIView):
    """This endpoint allows for creation of a transaction"""
    queryset = User.objects.all()
    serializer_class = ProfileSerializer


# def payment(request):
#     queryset = Transactions.objects.all()
#     last_element = queryset.reverse()[0]
#     user_id = request.user.id
#     user = User.objects.get(pk=user_id)
#     print(last_element)
#     print(user)


class PaymentView(PaymentRequestMixin):

    def get_pesapal_payment_iframe(self):
        '''
        Authenticates with pesapal to get the payment iframe src
        '''
        order_info = {
            'first_name': 'Some',
            'last_name': 'User',
            'amount': 100,
            'description': 'Payment for X',
            'reference': 2,  # some object id
            'email': 'mobmart@gmail.com',
        }

        iframe_src_url = self.get_payment_url(**order_info)
        return iframe_src_url


# def payment(request):
#     data = Transactions.objects.all()
#     print(data)
#     return HttpResponse(f"{Transactions.objects.all()}")


# @csrf_exempt
# def createTransaction(request):
#     if request.method == "POST":
#         trans_id = request.POST.get("trans_id", False)
#         print(trans_id)
#         sender_ref = request.POST.get("sender_ref", False)
#         country = request.POST.get("country", False)
#         amount = request.POST.get("amount", False)
#         transaction_type = request.POST.get("transaction_type", False)
#         currency = request.POST.get("currency", False)

#         transaction_data = Transactions(trans_id=trans_id, sender_ref=sender_ref, country=country,
#                                         amount=amount, transaction_type=transaction_type, currency=currency)
#         print(transaction_data)
#         # save transaction to db
#         transaction_id = transaction_data.save().id

#         # get current logging in user id
#         user_id = request.user.id

#         # query db to get this user
#         user = User.objects.get(pk=user_id)

#         # query db to get latest saved transaction
#         transaction = Transactions.objects.get(pk=transaction_id)

#         # add transaction to user
#         user.transactions.add(transaction)

#         return JsonResponse({
#             "trans_id": transaction.id,
#             "sender_ref": transaction.sender_ref,
#             "country": transaction.country,
#             "amount": transaction.amount,
#             "transaction_type": transaction.transaction_type,
#             "currency": transaction.currency
#         }, status=200)

#     return JsonResponse({
#         "Transaction": False,
#         "error": "endpoint only accept post request"
#     }, status=400)
