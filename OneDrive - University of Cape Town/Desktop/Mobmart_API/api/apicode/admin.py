from django.contrib import admin
from .models import User, Vendor, Transactions


admin.site.register([User, Vendor, Transactions, ])
