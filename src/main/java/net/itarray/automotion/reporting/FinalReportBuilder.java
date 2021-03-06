package net.itarray.automotion.reporting;

import com.webfirmframework.wffweb.tag.html.Body;
import com.webfirmframework.wffweb.tag.html.H1;
import com.webfirmframework.wffweb.tag.html.Html;
import com.webfirmframework.wffweb.tag.html.TitleTag;
import com.webfirmframework.wffweb.tag.html.attribute.Href;
import com.webfirmframework.wffweb.tag.html.attribute.Src;
import com.webfirmframework.wffweb.tag.html.attribute.Target;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attribute.global.Style;
import com.webfirmframework.wffweb.tag.html.images.Img;
import com.webfirmframework.wffweb.tag.html.links.A;
import com.webfirmframework.wffweb.tag.html.metainfo.Head;
import com.webfirmframework.wffweb.tag.html.programming.Script;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.StyleTag;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Mojo(name = "automotion-report")
public class FinalReportBuilder extends AbstractMojo {

    private static final String TARGET_AUTOMOTION = "target/automotion/";
    private static final String TARGET_AUTOMOTION_HTML = TARGET_AUTOMOTION + "html/";
    private static final String TARGET_AUTOMOTION_HTML_SUCESS = TARGET_AUTOMOTION_HTML + "success/";
    private static final String TARGET_AUTOMOTION_HTML_FAILURE = TARGET_AUTOMOTION_HTML + "failure/";
    private File[] listOfFilesSuccess = new File[0];
    private File[] listOfFilesFailure = new File[0];

    @Override
    public void execute() {
        LinkedHashMap<String, Boolean> files = new LinkedHashMap<>();

        File folderFailure = new File(TARGET_AUTOMOTION_HTML_FAILURE);
        if (folderFailure.exists()) {
            listOfFilesFailure = folderFailure.listFiles();
            if (listOfFilesFailure != null) {
                for (File file : listOfFilesFailure) {
                    files.put(file.getName(), false);
                }
            }
        }

        File folderSuccess = new File(TARGET_AUTOMOTION_HTML_SUCESS);
        if (folderSuccess.exists()) {
            listOfFilesSuccess = folderSuccess.listFiles();
            if (listOfFilesSuccess != null) {
                for (File file : listOfFilesSuccess) {
                    files.put(file.getName(), true);
                }
            }
        }

        if (files.size() > 0) {
            Html html = buildHtml(files);

            File report = new File(TARGET_AUTOMOTION + "index.html");
            report.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(report);
                 BufferedOutputStream bos = new BufferedOutputStream(fos)) {

                html.toOutputStream(bos);
                bos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private Html buildHtml(final LinkedHashMap<String, Boolean> files) {
        return new Html(null,
                new Style("background-color: #fff")) {{
            super.setPrependDocType(true);
            new Head(this) {{
                new TitleTag(this) {{
                    new NoTag(this, "Automotion report");
                }};
                new StyleTag(this) {{
                    new NoTag(this, ".accordion {\n" +
                            "    background-color: #f5f5f5;\n" +
                            "    color: #4d4d4d;\n" +
                            "    cursor: pointer;\n" +
                            "    padding: 18px;\n" +
                            "    width: 100%;\n" +
                            "    border-radius: 5px;\n" +
                            "    text-align: left;\n" +
                            "    outline: none;\n" +
                            "    font-size: 15px;\n" +
                            "    transition: 0.4s;\n" +
                            "    box-shadow: 1px 1px 5px grey;\n" +
                            "}\n" +
                            "\n" +
                            ".active, .accordion:hover {\n" +
                            "    background-color: #e4e4e4; \n" +
                            "    color: #4d4d4d; \n" +
                            "}\n" +
                            "\n" +
                            ".panel {\n" +
                            "    padding: 0;\n" +
                            "    min-height: 500px;\n" +
                            "    display: none;\n" +
                            "    background-color: white;\n" +
                            "}");
                }};
                new NoTag(this, "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
                new NoTag(this, "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\">");

                new NoTag(this, "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>");
                new NoTag(this, "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
                new NoTag(this, "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">");
                new NoTag(this, "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" crossorigin=\"anonymous\"></script>");
                new Script(this, new Src("https://cdn.plot.ly/plotly-latest.min.js"));
                new Script(this, new Src("https://cdnjs.cloudflare.com/ajax/libs/numeric/1.2.6/numeric.min.js"));
            }};

            new Body(this,
                    new Style("background-color: #fff")) {{
                new Div(this, new ClassAttribute("container-fluid")) {{
                    new Div(this,
                            new ClassAttribute("row"),
                            new Style("padding: 5px; background-color: slategrey;")) {{
                        new Img(this,
                                new Style("width: 90px"),
                                new ClassAttribute("col-md-1"),
                                new Src("https://automotion.itarray.net/wp-content/uploads/2017/09/automotion_logo@256.png"));
                        new Div(this,
                                new ClassAttribute("col-md-8"),
                                new Style("color: white;")) {{
                            new H1(this,
                                    new Style("font-size:22px; font-weight: 200;")) {{
                                new NoTag(this, String.format("Automotion report from: %s", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
                            }};
                        }};
                    }};
                    new Div(this,
                            new ClassAttribute("row")) {{
                        new Div(this,
                                new ClassAttribute("col-xs-12 col-sm-6 col-md-4"),
                                new Id("plot")) {{
                        }};
                    }};
                    for (final Map.Entry<String, Boolean> entry : files.entrySet()) {

                        final String s = entry.getKey();
                        String statusFolder = "success/";
                        if (!entry.getValue()) {
                            statusFolder = "failure/";
                        }

                        final String finalStatusFolder = statusFolder;

                        new Div(this,
                                new ClassAttribute("row"),
                                new Style("background-color: #fff; padding: 10px; margin-top: 3px")) {{
                            new Div(this,
                                    new ClassAttribute("accordion"),
                                    new Style("width: auto; text-align:left; font-size: 28px; font-weight: 300")) {{
                                new NoTag(this, s);
                                new A(this,
                                        new Style("float: right"),
                                        new ClassAttribute("btn " + (entry.getValue() ? "btn-success" : "btn-danger")),
                                        new Href("html/" + finalStatusFolder + s),
                                        new Target("target")
                                ) {{
                                    new NoTag(this, "Fullscreen");
                                }};
                            }};


                            new Div(this,
                                    //new Style("background: #f5f5f5"),
                                    new ClassAttribute("panel")) {{
                                new NoTag(this, "html/" + finalStatusFolder + s);
//                                new IFrame(this,
//                                        new Style("width: 100%; height: 1000px"),
//                                        new Src("html/" + finalStatusFolder + s));

                            }};
                        }};
                    }
                }};

                new Script(this) {{
                    new NoTag(this, "" +
                            "var data = [{\n" +
                            "  values: [" + listOfFilesSuccess.length + ", " + listOfFilesFailure.length + "],\n" +
                            "  labels: ['Passed', 'Failed'],\n" +
                            "  type: 'pie',\n" +
                            "  marker: {colors: ['rgb(60,179,113)', 'rgb(255,99,71)']},\n" +
                            "  hole: .4\n" +
                            "}];\n" +
                            "\n" +
                            "var layout = {\n" +
                            "  title: 'Stats of test suites',\n" +
                            "  height: 400,\n" +
                            "  width: 500,\n" +
                            "};\n" +
                            "\n" +
                            "Plotly.newPlot('plot', data, layout);");
                }};

                new Script(this) {{
                    new NoTag(this, "var acc = document.getElementsByClassName(\"accordion\");\n" +
                            "var i;\n" +
                            "\n" +
                            "for (i = 0; i < acc.length; i++) {\n" +
                            "    acc[i].addEventListener(\"click\", function() {\n" +
                            "        this.classList.toggle(\"active\");\n" +
                            "        var panel = this.nextElementSibling;\n" +
                            "        if (panel.style.display === \"block\") {\n" +
                            "            panel.style.display = \"none\";\n" +
                            "        } else {\n" +
                            "            panel.style.display = \"block\";\n" +
                            "            if (panel.textContent != \"\") {\n" +
                            "            var iframe = document.createElement(\"iframe\");\n" +
                            "            iframe.src = panel.textContent;\n" +
                            "            iframe.style = \"width: 100%; height: 500px\";" +
                            "            panel.textContent = \"\";" +
                            "            panel.append(iframe);\n" +
                            "        }\n" +
                            "        }\n" +
                            "    });\n" +
                            "}");
                }};
            }};
        }};
    }
}
