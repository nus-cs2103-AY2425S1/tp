---
layout: page
title: User Guide
---

# Welcome to BakeBuddy

BakeBuddy is your all-in-one command-line companion for managing your home bakery business. Designed with speed and
efficiency in mind, it combines the power of a Command Line Interface (CLI) with intuitive features to help you focus
on what matters most - creating delicious baked goods.

BakeBuddy is a desktop application that streamlines your bakery operations by helping you manage:
- 🧁 Pastries and recipes
- 👥 Customers and their orders
- 📦 Suppliers and ingredients
- 📋 Order tracking and fulfillment
- 🗄️ Inventory management

## Why Choose BakeBuddy?

- **Speed First**: Execute commands quickly through our CLI, perfect for busy bakers
- **User-Friendly**: Simple GUI elements complement the CLI for enhanced usability
- **All-in-One Solution**: Manage every aspect of your bakery business from a single application
- **Efficiency Focused**: Designed specifically for home-based bakery owners who value their time

## Getting Started

This guide will walk you through everything you need to know about BakeBuddy, from basic commands to advanced features.

* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## Before You Begin ✔️

### Step 1: Check if Your Computer is Ready
First, we need to make sure your computer has Java 17 installed. Here's how to check:

1. Open your computer's terminal:
    - **For Windows**: Press the Windows key + R, type `cmd`, and press Enter
    - **For Mac**: Press Command + Space, type `terminal`, and press Enter

2. In the black window that appears, type exactly:
   ```
   java --version
   ```
   and press Enter

3. What you should see:
    - ✅ If you see "Java version 17" (or any number above 17), you're ready to go!
    - ❌ If you see "command not found" or a number below 17, visit [Java's download page](https://www.oracle.com/java/technologies/downloads/#java17) to install Java 17

### Step 2: Install BakeBuddy

1. Download BakeBuddy:
    - Click [this link](https://github.com/AY2425S1-CS2103T-T11-1/tp/releases) to download the latest BakeBuddy
    - Look for the file named `bakebuddy.jar`
    - Click on it to download

2. Create a home for BakeBuddy:
    - Create a new folder on your computer named `BakeBuddy`
    - Move the downloaded `bakebuddy.jar` file into this folder

3. Start BakeBuddy:
    - Open your terminal (like in Step 1)
    - Type `cd ` (with a space after cd)
    - Drag your BakeBuddy folder into the terminal window (this fills in the location automatically!)
    - Press Enter
    - Type:
      ```
      java -jar bakebuddy.jar
      ```
    - Press Enter

   You should see the BakeBuddy window appear!

### Step 3: Try Your First Commands

Now that BakeBuddy is running, let's add your first items. In the BakeBuddy window, you'll see a space to type commands at the top.

1. Add your first ingredient:
   ```
   addPastry
   ```
   ↳ This adds a chocolate cake that costs $45.00 and marks it as a bestseller

2. Add your first customer:
   ```
   addCustomer n/Jane Doe p/98765432 e/jane@email.com
   ```
   ↳ This adds a customer named Jane with her phone number and email

3. See what you've added:
    - Type `list pastries` to see your pastry
    - Type `list customers` to see your customer

Refer to the [Features](#features) below for details of each command.

## Features

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`
