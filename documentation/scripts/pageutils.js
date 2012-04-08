/*
 * © 2011, David F. Houghton
 * 
 * licensed under LGPL v3: http://www.gnu.org/copyleft/lesser.html
 */

var dfh = {
	/**
	 * Performs all page preparation. Should be invoked on page load.
	 * 
	 * @param self
	 *            identifier for current page (see headers(self, base))
	 * @param base
	 *            page naming base
	 */
	prepare : function(self, base) {
		this.headers(self, base);
		this.toc();
		this.footnotes();
		this.mdash();
		this.version();
	},

	/**
	 * A little function to facilitate the use of footnotes on a page.
	 * Procedure:
	 * 
	 * 1) You create a style sheet with 3 styles
	 * 
	 * a) a.fn { display:none; ... } for the footnotes you write in-line
	 * 
	 * b) a.footnote { font-size: small; ... } for the footnotes automatically
	 * created by this script
	 * 
	 * c) div#footnotes { margin-top: 1em; padding-top: 1em; border-top: thin
	 * solid black; ... } for the section where the footnote text will appear
	 * 
	 * 2) In the body of your document wherever you want a footnote you enclose
	 * the text like so: lorem ipsum<span class="fn">sic transit gloria mundi</span>
	 * etc.
	 * 
	 * 3) You trigger footnotes() on page load like so: <body
	 * onLoad="footnotes()">...
	 * 
	 * 4) If you want to control the placement of your footnotes, where you want
	 * to footnotes to appear you include the following: <div id="footnotes"></div>.
	 * Otherwise, the footnotes DIV will be appended to the list of child nodes
	 * of the body element, so they will appear last.
	 * 
	 * Provided you have linked this script to the page it will modify the page
	 * contents, replacing your invisible footnote anchor elements with links to
	 * appropriate elements in the footnote section and vice versa. If it finds
	 * no footnotes on the page, it will delete the footnotes section.
	 */
	footnotes : function() {
		var fnSection = document.getElementById("footnotes");
		var fnsExists = fnSection && 1;
		if (!fnsExists) {
			fnSection = document.createElement('div');
			fnSection.setAttribute('id', 'footnotes');
		}
		var fns = document.getElementsByClassName("fn");
		// copy items to new array that won't be dynamically altered
		var ar = new Array();
		var len = fns.length;
		for ( var i = 0; i < len; i++) {
			var span = fns[i];
			ar[i] = span;
		}
		var found = false;
		var index = 1;
		for ( var i = 0; i < len; i++) {
			var span = ar[i];
			if (span.nodeName == 'SPAN') {
				found = true;
				span.removeAttribute('class');
				var bname = 'fn' + index + '_bottom';
				var tname = 'fn' + index + '_top';
				var topA = document.createElement("a");
				topA.setAttribute('href', '#' + bname);
				topA.setAttribute('name', tname);
				topA.setAttribute('class', 'footnote');
				topA.innerHTML = index;
				span.parentNode.replaceChild(topA, span);
				var textDiv = document.createElement("div");
				var bottomA = document.createElement("a");
				bottomA.setAttribute('href', '#' + tname);
				bottomA.setAttribute('name', bname);
				bottomA.setAttribute('class', 'footnote');
				bottomA.innerHTML = index;
				textDiv.appendChild(bottomA);
				textDiv.appendChild(document.createTextNode(' '));
				textDiv.appendChild(span);
				fnSection.appendChild(textDiv);
				index++;
			}
			if (found && !fnsExists)
				document.body.appendChild(fnSection);
		}
	},

	/**
	 * Creates a header for the top of the page -- a list of other files on the
	 * site -- based on the contents of a hdrs object provided elsewhere. It
	 * also renames the page to have the title 'base :: self'.
	 * 
	 * @param self
	 *            identifier for current page
	 * @param base
	 *            page title base
	 */
	headers : function(self, base) {
		document.title = base + " :: " + self;
		var div = document.createElement("div");
		div.setAttribute("style",
				"margin: 1.5em auto; text-align: center; white-space: nowrap");
		var nonInitial = false;
		for ( var key in hdrs) {
			if (nonInitial) {
				var span = document.createElement("span");
				span.setAttribute('style', "margin: 0 1em");
				span.appendChild(document.createTextNode("|"));
				div.appendChild(span);
			} else
				nonInitial = true;
			var e;
			if (key == self)
				e = document.createTextNode(key);
			else {
				var a = document.createElement("a");
				a.setAttribute("href", hdrs[key]);
				a.appendChild(document.createTextNode(key));
				e = a;
			}
			div.appendChild(e);
		}
		if (document.body.childNodes.length)
			document.body.insertBefore(div, document.body.firstChild);
		else
			document.body.appendChild(div);
	},

	/**
	 * Searches the document for the string __VERSION__ in text nodes and
	 * attributes and replaces it with the version value.
	 */
	version : function() {
		// only proceed if the version variable has been set
		if (version) {
			var regex = /__VERSION__/g;
			var recReplace = function(n) {
				if (n.nodeType == 3) { // text
					if (regex.test(n.data)) {
						var replacement = n.data.replace(regex, version);
						var t = document.createTextNode(replacement);
						n.parentNode.insertBefore(t, n);
						n.parentNode.removeChild(n);
					}
				} else if (n.nodeType == 1) { // element
					for ( var i = 0; i < n.childNodes.length; i++) {
						recReplace(n.childNodes[i]);
					}
					for (i = 0; i < n.attributes.length; i++) {
						var a = n.attributes[i];
						// hack
						// for some reason, this only works for Firefox if I use
						// an in-line regex; webkit required me to test the
						// expression twice but otherwise worked
						if (/__VERSION__/g.test(a.nodeValue)) {
							a.nodeValue = a.nodeValue.replace(regex, version);
						}
					}
				}
			};
			recReplace(document.body);
		}
	},

	/**
	 * Inserts a table of contents in the document. Procedure:
	 * 
	 * 1) You optionally create a style sheet with 1 style
	 * 
	 * div#toc { ... }
	 * 
	 * 2) In the body of your document where you want the table of contents you
	 * put a span element with the id 'toc': <span id="toc"></span>
	 * 
	 * 3) You trigger toc() on page load like so: <body onLoad="toc()">...
	 * 
	 * Provided you have linked this script to the page it will modify the page
	 * contents, replacing the empty 'toc' span with a 'toc' div. Every header
	 * *which is a child of the body element* will have a link in this table of
	 * contents. The identation of this line will be a multiple of the number of
	 * the corresponding header. The headers themselves will all have a 'top'
	 * link which takes you back to the table of contents.
	 */
	toc : function() {
		var tocSpan = document.getElementById("toc");
		if (tocSpan) {
			var re = /\bh\d+\b/i;
			var n = tocSpan;
			while (n.parentNode != document.body)
				n = n.parentNode;
			var tocDiv = document.createElement("div");
			tocDiv.setAttribute("id", "toc");
			var tocHeader = document.createElement("h3");
			tocHeader.setAttribute("style", "text-align:center");
			tocDiv.appendChild(tocHeader);
			tocHeader.appendChild(document.createTextNode("Table of Contents"));
			document.body.insertBefore(tocDiv, tocSpan);
			while (n) {
				if (re.test(n.nodeName)) {
					var indent = n.nodeName.substring(1, n.nodeName.length);
					indent += 'em';
					var id = this.stringify(n);
					id = id.replace(/^\s+|\s+$/g, '').replace(/\s+/g, ' ');
					id = this.makeUnique(id);
					var tocLine = document.createElement("a");
					tocLine.setAttribute("style",
							"display: block; margin-left: " + indent);
					tocLine.setAttribute("href", "#" + id);
					tocDiv.appendChild(tocLine);
					for ( var i = 0; i < n.childNodes.length; i++)
						tocLine.appendChild(n.childNodes[i].cloneNode(true));
					var name = document.createElement("a");
					name.setAttribute("id", id);
					document.body.insertBefore(name, n);
					var top = document.createElement("a");
					top.setAttribute("href", "#toc");
					top.setAttribute('class', 'toc_top');
					top.innerHTML = 'top';
					n.appendChild(top);
				}
				n = n.nextSibling;
			}
			document.body.removeChild(tocSpan);
		}
	},

	/**
	 * Finds a unique name for an anchor element.
	 * 
	 * @param id
	 *            prospective anchor name
	 * @returns anchor name unique within document
	 */
	makeUnique : function(id) {
		var index = 0;
		var uid = id;
		OUTER: while (true) {
			for ( var i = 0; i < document.anchors.length; i++) {
				var a = document.anchors[i];
				if (a.name && a.name == uid) {
					uid = id + index++;
					continue OUTER;
				}
			}
			break;
		}
		return uid;
	},

	/**
	 * Browser-neutral code for stringifying an element.
	 * 
	 * @param node
	 * @returns node text content
	 */
	stringify : function(node) {
		if (node.nodeType == Node.TEXT_NODE)
			return node.data;
		var id = "";
		for ( var i = 0; i < node.childNodes.length; i++)
			id += this.stringify(node.childNodes[i]);
		return id;
	},

	/**
	 * we look for -- and replace it with an em dash
	 * except in pre and code elements
	 * only -- is transformed, not ---, etc.
	 */
	mdash : function() {
		var walker = document.createTreeWalker(document.body,
				NodeFilter.SHOW_ALL, function(node) {
					if (node.tagName == "PRE" || node.tagName == "CODE")
						return NodeFilter.FILTER_REJECT;
					return NodeFilter.FILTER_ACCEPT;
				}, false);
		while (walker.nextNode()) {
			if (walker.currentNode.nodeType == 3
					&& /--/.test(walker.currentNode.nodeValue)) {
				var n = walker.currentNode;
				var str = n.nodeValue;
				str = str.replace(/(^|[^-])--(?!-)/mg, '$1\u2014');
				n.nodeValue = str;
			}
		}
	},
};