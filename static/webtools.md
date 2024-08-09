---
layout: default
title: ESP Web Tools
header_type: post
show_breadcrumb: false
---

# Installation

You can use the button below to install the pre-built firmware directly to your device via USB from the browser. Note this has already been done from the factory. You only need to do this if you're trying to start fresh, or grab the latest updates.

You can also use it to get device info, check which version is installed, view logs, etc.

    <label><input type="radio" name="type" value="bpmk1" />Bed Presence Mk1</label>
    <label><input type="radio" name="type" value="test" />2nd Test Device</label>

<p class="button-row" align="center">
    <esp-web-install-button class="invisible"></esp-web-install-button>
</p>

<script type="module" src="https://unpkg.com/esp-web-tools@10/dist/web/install-button.js?module"></script>
<script>
  document.querySelectorAll('input[name="type"]').forEach(radio =>
    radio.addEventListener("change", () => {
      const button = document.querySelector('esp-web-install-button');
      button.manifest = `https://docs.elevatedsensors.com/${radio.value}-manifest.json`;
      button.classList.remove('invisible');
    }
  ));
</script>
