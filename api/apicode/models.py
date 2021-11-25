from datetime import datetime
from django.contrib.auth.models import AbstractUser
# from django.contrib.gis.db import models
from django.db import models
from django.db.models.deletion import CASCADE


class Transactions(models.Model):
    trans_id = models.CharField(max_length=20)
    sender_ref = models.CharField(max_length=20)
    country = models.CharField(max_length=30, null=True)
    amount = models.PositiveIntegerField()
    transaction_type = models.CharField(max_length=20)
    currency = models.CharField(max_length=20)


class User(AbstractUser):
    # transactions = models.ForeignKey(
    #     Transactions, on_delete=CASCADE, null=True)
    isVendor = models.BooleanField(default=False)
    first_name = models.CharField(max_length=20)
    last_name = models.CharField(max_length=20)
    profile_picture = models.ImageField(upload_to="images/", null=True)
    country = models.CharField(max_length=20, null=True)
    mobile_money_provider = models.CharField(max_length=20, null=True)
    phone_number = models.CharField(max_length=20, null=True)
    date_of_birth = models.DateTimeField(default=datetime.now, blank=True)


class Vendor(models.Model):
    business_name = models.CharField(max_length=20)
    # business_location = models.PointField()
    profile_picture = models.ImageField(upload_to="images/")
    description = models.CharField(max_length=20)
    mobile_money_provider = models.CharField(max_length=20)
    phone_number = models.CharField(max_length=20)
    is_verified = models.BooleanField(default=False)
    account_number = models.CharField(max_length=20)
